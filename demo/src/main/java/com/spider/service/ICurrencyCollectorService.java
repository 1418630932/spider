package com.spider.service;

import com.spider.entity.CurrencyInfo;
import com.spider.entity.CurrencySummaryDto;

import java.util.List;

/** 土狗信息收集服务
 * @author zhuliyang
 * @date 2022-02-20
 * @time 23:25
 **/
public interface ICurrencyCollectorService {
    List<CurrencySummaryDto> collectorList(List<CurrencyInfo> currencyInfoList);

    CurrencySummaryDto collectorOne(CurrencyInfo currencyInfo);
}
