package com.spider.service;

import com.spider.entity.CurrencyInfo;

import java.util.List;

/** 土狗消息通知处理类
 * @author zhuliyang
 * @date 2022-02-20
 * @time 23:25
 **/
public interface ICurrencyEventNotifyService {
    String buildContent(List<CurrencyInfo> currencyInfoList);

    void dingDingNotify(String content);
}
