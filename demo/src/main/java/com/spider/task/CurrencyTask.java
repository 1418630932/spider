package com.spider.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.client.DexToolClient;
import com.spider.client.DingdingNotifyClient;
import com.spider.entity.Area;
import com.spider.entity.CurrencyInfo;
import com.spider.entity.HouseInfo;
import com.spider.log.MyLog;
import com.spider.model.currency.Currency;
import com.spider.model.currency.Info;
import com.spider.model.currency.Token;
import com.spider.model.dingding.NotifyDO;
import com.spider.model.dingding.TextDO;
import com.spider.processor.LianJiaProcessor;
import com.spider.service.IAreaService;
import com.spider.service.ICurrencyInfoService;
import com.spider.service.IHouseInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 爬虫的定时任务类
 *
 * @author zhuliyang
 * @date 2020-03-09
 * @time 22:44
 **/
@Component
public class CurrencyTask {
    @Autowired
    private DexToolClient dexToolClient;
    @Autowired
    private ICurrencyInfoService currencyInfoService;
    @Autowired
    private DingdingNotifyClient dingdingNotifyClient;

    private static final String NOTIFY_URL ="https://oapi.dingtalk.com/robot/send?access_token=5b82349f101497fba27dde0dd8b7e675ec44aaeb776d429dcdfe3b22797a871e";
    //容器启动后10秒开始执行 然后隔5分钟再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 5*60*1000)
    public void start() {
        MyLog.logInfo("开始爬虫");
        List<Currency> hotCurrency = dexToolClient.getHotCurrency();
        List<Currency> filterList = hotCurrency.stream().filter(data -> data.getInfo() != null).collect(Collectors.toList());
        List<String> hotIdList = filterList.stream().map(Currency::getInfo).map(Info::getAddress).collect(Collectors.toList());
        QueryWrapper<CurrencyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",hotIdList);
        List<CurrencyInfo> existedList = currencyInfoService.list(queryWrapper);

        if (CollectionUtils.isEmpty(existedList)){
            List<CurrencyInfo> currencyInfoList = transferCurrencyInfoList(filterList);
            if (CollectionUtils.isNotEmpty(currencyInfoList)){
                currencyInfoService.saveBatch(currencyInfoList);
                String content = buildContent(currencyInfoList);
                if (StringUtils.isNoneBlank(content)){
                    dingDingNotify(content);
                }
            }
        }else {
            Set<String> existsSet = existedList.stream().map(CurrencyInfo::getId).collect(Collectors.toSet());
            List<Currency> remainList = filterList.stream().filter(currency -> !existsSet.contains(currency.getInfo().getAddress())).collect(Collectors.toList());
            List<CurrencyInfo> currencyInfoList = transferCurrencyInfoList(remainList);
            if (CollectionUtils.isNotEmpty(currencyInfoList)){
                currencyInfoService.saveBatch(currencyInfoList);
                String content = buildContent(currencyInfoList);
                if (StringUtils.isNoneBlank(content)){
                    dingDingNotify(content);
                }
            }
        }
        MyLog.logInfo("结束爬虫");
    }

    private List<CurrencyInfo> transferCurrencyInfoList(List<Currency> currencyInfo ){
        if (CollectionUtils.isEmpty(currencyInfo)){
            MyLog.logInfo("empty currencyInfo");
            return new ArrayList<>();
        }
        List<CurrencyInfo> res = new ArrayList<>();
        for (Currency currency : currencyInfo) {
            CurrencyInfo dto = transferCurrencyInfo(currency);
            if (dto!=null){
                res.add(dto);
            }
        }
        return res;
    }

    private CurrencyInfo transferCurrencyInfo(Currency currency){
        if (currency==null){
            MyLog.logInfo("currency is null");
            return null;
        }
        CurrencyInfo currencyInfo = new CurrencyInfo();
        String token = Optional.ofNullable(currency.getInfo()).map(Info::getAddress).orElse(null);
        currencyInfo.setId(token);
        currencyInfo.setToken(token);
        currencyInfo.setHolder(Optional.ofNullable(currency.getInfo()).map(Info::getHolders).orElse(null));

        String name0 = Optional.ofNullable(currency.getToken0()).map(Token::getSymbol).orElse("null");
        String name1 = Optional.ofNullable(currency.getToken1()).map(Token::getSymbol).orElse("null");
        currencyInfo.setName(name0+"-"+name1);

        currencyInfo.setCreateTime(Optional.ofNullable(currency.getCreatedAtTimestamp())
                .map(timestamp->timestamp*1000)
                .map(Date::new)
                .map(Date::toInstant)
                .map(instant -> instant.atOffset(ZoneOffset.of("+8")))
                .map(OffsetDateTime::toLocalDateTime)
                .orElse(null));
        return currencyInfo;
    }

    private String buildContent(List<CurrencyInfo> currencyInfoList){
        if (CollectionUtils.isEmpty(currencyInfoList)){
            return null;
        }
        StringBuilder content = new StringBuilder();
        for (CurrencyInfo currencyInfo : currencyInfoList) {
            content.append(currencyInfo.getName()).append(":")
                    .append(currencyInfo.getToken())
                    .append("  holder:").append(currencyInfo.getHolder())
                    .append("\n");
        }
        return content.toString();
    }

    private void dingDingNotify(String content){
        NotifyDO notifyDO = new NotifyDO();
        notifyDO.setMsgtype("text");
        TextDO textDO = new TextDO();
        textDO.setContent("土狗"+"\n"+content);
        notifyDO.setText(textDO);
        dingdingNotifyClient.notify(NOTIFY_URL,notifyDO);
    }
}
