package com.spider.client;

import com.spider.model.dingding.NotifyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;

/**
 * @author zhuliyang
 * @date 2021-07-31
 * @time 23:13
 **/
@Repository
public class DingdingNotifyClient {
    @Autowired
    private BaseHttpClient baseHttpClient;

    public void notify(String url,NotifyDO notifyDO){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        baseHttpClient.post(url, httpHeaders, notifyDO, String.class);
    }
}
