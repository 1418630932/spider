package com.spider.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.client.DexToolClient;
import com.spider.entity.Area;
import com.spider.entity.HouseInfo;
import com.spider.log.MyLog;
import com.spider.model.currency.Currency;
import com.spider.processor.LianJiaProcessor;
import com.spider.service.IAreaService;
import com.spider.service.IHouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    //容器启动后10秒开始执行 然后隔5分钟再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 5*60*1000)
    public void start() {
        MyLog.logInfo("开始爬虫");
        List<Currency> hotCurrency = dexToolClient.getHotCurrency();
        System.out.println(hotCurrency);
        MyLog.logInfo("结束爬虫");
    }
}
