package com.spider.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.client.DexToolClient;
import com.spider.entity.CurrencyInfo;
import com.spider.entity.CurrencySummaryDto;
import com.spider.log.MyLog;
import com.spider.model.currency.Currency;
import com.spider.model.currency.Info;
import com.spider.model.currency.Token;
import com.spider.service.ICurrencyEventNotifyService;
import com.spider.service.ICurrencyInfoService;
import com.spider.service.impl.ICurrencyCollectorServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
    private ICurrencyCollectorServiceImpl currencyCollectorService;
    @Autowired
    private ICurrencyEventNotifyService  currencyEventNotifyService;
 //容器启动后10秒开始执行 然后隔5分钟周期执行
    @Scheduled(initialDelay = 1000, fixedDelay = 5*60*1000)
    public void start() {
        MyLog.logInfo("开始爬虫");
        List<Currency> hotCurrency = dexToolClient.getHotCurrency();
        //过滤数据格式非法的数据
        List<Currency> filterList = hotCurrency.stream().filter(data -> data.getInfo() != null).collect(Collectors.toList());
        //查询数据库存在的数据 目的是为了挖掘新的土狗 不要过度推送 3天之内推送的不要重复推送
        List<String> hotTokenList = filterList.stream().map(Currency::getInfo).map(Info::getAddress).collect(Collectors.toList());
        QueryWrapper<CurrencyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("token",hotTokenList);
        queryWrapper.gt("publish_time", new Date(System.currentTimeMillis()-3*24*60*60*1000));
        List<CurrencyInfo> existedList = currencyInfoService.list(queryWrapper);

        if (CollectionUtils.isEmpty(existedList)){
            List<CurrencyInfo> currencyInfoList = transferCurrencyInfoList(filterList);
            if (CollectionUtils.isNotEmpty(currencyInfoList)){
                currencyInfoService.saveBatch(currencyInfoList);
                List<CurrencySummaryDto> currencySummaryDtos = currencyCollectorService.collectorList(currencyInfoList);
                String content = currencyEventNotifyService.buildContent(currencySummaryDtos);
                if (StringUtils.isNoneBlank(content)){
                    currencyEventNotifyService.dingDingNotify(content);
                }
            }
        }else {
            //数据库已存在的币
            Set<String> existsSet = existedList.stream().map(CurrencyInfo::getToken).collect(Collectors.toSet());
            List<Currency> remainList = filterList.stream().filter(currency -> !existsSet.contains(currency.getInfo().getAddress())).collect(Collectors.toList());
            List<CurrencyInfo> currencyInfoList = transferCurrencyInfoList(remainList);
            if (CollectionUtils.isNotEmpty(currencyInfoList)){
                currencyInfoService.saveBatch(currencyInfoList);
                List<CurrencySummaryDto> currencySummaryDtos = currencyCollectorService.collectorList(currencyInfoList);
                String content = currencyEventNotifyService.buildContent(currencySummaryDtos);
                if (StringUtils.isNoneBlank(content)){
                    currencyEventNotifyService.dingDingNotify(content);
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
        currencyInfo.setId(currency.getId());
        String token = Optional.ofNullable(currency.getInfo()).map(Info::getAddress).orElse(null);
        currencyInfo.setToken(token);
        currencyInfo.setHolder(Optional.ofNullable(currency.getInfo()).map(Info::getHolders).orElse(null));

        String name0 = Optional.ofNullable(currency.getToken0()).map(Token::getSymbol).orElse("null");
        String name1 = Optional.ofNullable(currency.getToken1()).map(Token::getSymbol).orElse("null");
        currencyInfo.setName(name0+"-"+name1);
        Integer liquidity = Optional.ofNullable(currency.getLiquidity()).map(Double::intValue).orElse(null);
        currencyInfo.setLiquidity(liquidity);

        currencyInfo.setCreateTime(Optional.ofNullable(currency.getCreatedAtTimestamp())
                .map(timestamp->timestamp*1000)
                .map(Date::new)
                .map(Date::toInstant)
                .map(instant -> instant.atOffset(ZoneOffset.of("+8")))
                .map(OffsetDateTime::toLocalDateTime)
                .orElse(null));
        currencyInfo.setPublishTime(LocalDateTime.now(ZoneId.systemDefault()));
        return currencyInfo;
    }
}
