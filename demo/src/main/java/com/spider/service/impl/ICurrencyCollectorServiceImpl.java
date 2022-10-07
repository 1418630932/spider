package com.spider.service.impl;

import com.spider.client.DexToolClient;
import com.spider.entity.CurrencyInfo;
import com.spider.entity.CurrencySummaryDto;
import com.spider.model.currency.PairSummary;
import com.spider.service.ICurrencyCollectorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** 土狗消息通知处理类
 * @author zhuliyang
 * @date 2022-02-20
 * @time 23:25
 **/
@Service
public class ICurrencyCollectorServiceImpl implements ICurrencyCollectorService {

    @Autowired
    private DexToolClient dexToolClient;

    @Override
    public List<CurrencySummaryDto> collectorList(List<CurrencyInfo> currencyInfoList) {
        if (CollectionUtils.isEmpty(currencyInfoList)){
            return new ArrayList<>();
        }
        List<CurrencySummaryDto> res  =   new ArrayList<>();
        for (CurrencyInfo currencyInfo : currencyInfoList) {
            CurrencySummaryDto currencySummaryDto = collectorOne(currencyInfo);
            if (currencySummaryDto!=null){
                res.add(currencySummaryDto);
            }
        }
        return res;
    }

    @Override
    public CurrencySummaryDto collectorOne(CurrencyInfo currencyInfo) {
        if (currencyInfo==null){
            return null;
        }
        CurrencySummaryDto currencySummaryDto = new CurrencySummaryDto();
        currencySummaryDto.setId(currencyInfo.getId());
        currencySummaryDto.setName(currencyInfo.getName());
        currencySummaryDto.setToken(currencyInfo.getToken());
        currencySummaryDto.setHolder(currencyInfo.getHolder());
        currencySummaryDto.setLiquidity(currencyInfo.getLiquidity());
        currencySummaryDto.setCreateTime(currencyInfo.getCreateTime());

        PairSummary pairSummary = dexToolClient.getPairSummary(currencyInfo.getId());
        Double price = pairSummary.getPrice();
        Double price24h = pairSummary.getPrice24h();
        String changePercent = String.format("%.2f",(price-price24h)/price24h*100)+"%";
        currencySummaryDto.setChange24H(changePercent);
        return currencySummaryDto;
    }
}
