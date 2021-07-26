package com.spider.client;

import com.alibaba.fastjson.JSON;
import com.spider.entity.CurrencyInfo;
import com.spider.model.currency.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Repository
@Slf4j
public class BaseHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * get方式提交
     * @param url 请求路径
     * @param formEntity 请求头+参数
     * @return
     */
    public  <T,P>T get(String url, HttpEntity<P> formEntity, Class<T> returnClass) {
        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, formEntity, returnClass);
        } catch (RestClientException e) {
            log.error("restTemplate.exchange error url={},formEntity={}",url, JSON.toJSONString(formEntity),e);
            return null;
        }
        return responseEntity.getBody();
    }


    /**
     * post 方式提交
     * @param url 请求路径
     * @param headers 请求头
     * @param params 请求参数
     * @return
     */
    public  <T,P>T post(String url, HttpHeaders headers, P params, Class<T> returnClass) {
        HttpEntity<P> formEntity = new HttpEntity<>(params,headers);
        T response = null;
        try {
            response = restTemplate.postForObject(url,formEntity, returnClass);
        } catch (RestClientException e) {
            log.error("restTemplate.postForObject error url={},formEntity={}",url, JSON.toJSONString(formEntity),e);
            return null;
        }
        return response;
    }

}