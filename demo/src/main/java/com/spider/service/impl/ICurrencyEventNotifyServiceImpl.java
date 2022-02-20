package com.spider.service.impl;

import com.spider.client.DingdingNotifyClient;
import com.spider.entity.CurrencyInfo;
import com.spider.entity.CurrencySummaryDto;
import com.spider.model.dingding.NotifyDO;
import com.spider.model.dingding.TextDO;
import com.spider.service.ICurrencyEventNotifyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 土狗消息通知处理类
 * @author zhuliyang
 * @date 2022-02-20
 * @time 23:25
 **/
@Service
public class ICurrencyEventNotifyServiceImpl implements ICurrencyEventNotifyService {
    private static final String NOTIFY_URL ="https://oapi.dingtalk.com/robot/send?access_token=5b82349f101497fba27dde0dd8b7e675ec44aaeb776d429dcdfe3b22797a871e";

    private static final String TOKEN_DETAIL_URL ="https://www.dextools.io/app/bsc/pair-explorer/";
    @Autowired
    private DingdingNotifyClient dingdingNotifyClient;

    @Override
    public String buildContent(List<CurrencySummaryDto> currencySummaryDtoList) {
        if (CollectionUtils.isEmpty(currencySummaryDtoList)){
            return null;
        }
        StringBuilder content = new StringBuilder();
        for (CurrencySummaryDto currencySummaryDto : currencySummaryDtoList) {
            content.append("name: ").append(currencySummaryDto.getName()).append("\n")
                    .append("token: ").append(currencySummaryDto.getToken()).append("\n")
                    .append("持有人:").append(currencySummaryDto.getHolder()).append("\n")
                    .append("池子大小:").append("$ ").append(currencySummaryDto.getLiquidity()).append("\n")
                    .append("24H涨幅:").append(currencySummaryDto.getChange24H()).append("\n")
                    .append("开盘时间:").append(currencySummaryDto.getCreateTime()).append("\n")
                    .append(TOKEN_DETAIL_URL).append(currencySummaryDto.getId()).append("\n")
                    .append("\n");
        }
        return content.toString();
    }

    @Override
    public void dingDingNotify(String content) {
        NotifyDO notifyDO = new NotifyDO();
        notifyDO.setMsgtype("text");
        TextDO textDO = new TextDO();
        textDO.setContent("土狗"+"\n"+content);
        notifyDO.setText(textDO);
        dingdingNotifyClient.notify(NOTIFY_URL,notifyDO);
    }


}
