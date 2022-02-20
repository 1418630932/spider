package com.spider.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.spider.log.MyLog;
import com.spider.model.currency.Currency;
import com.spider.model.currency.PairSummary;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuliyang
 * @date 2021-07-26
 * @time 23:00
 **/
@Repository
public class DexToolClient {
    @Autowired
    private BaseHttpClient baseHttpClient;

    private static final String HOT_URL = "https://www.dextools.io/chain-bsc/api/dashboard/pancakeswap/hot";

    private static final String PAIR_SUMMARY_URL = "https://www.dextools.io/chain-bsc/api/pair/summary?address=";

    public List<Currency> getHotCurrency(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        String json = baseHttpClient.get(HOT_URL, httpEntity, String.class);
        MyLog.logInfo("http result is"+JSON.toJSONString(json));
        List<Currency> currencyList = JSON.parseArray(json, Currency.class);
        return currencyList;
    }


    public PairSummary getPairSummary(String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        String json = baseHttpClient.get(PAIR_SUMMARY_URL+id, httpEntity, String.class);
        PairSummary pairSummary = JSON.parseObject(json, PairSummary.class);
        return pairSummary;
    }

}
