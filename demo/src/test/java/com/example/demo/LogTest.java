package com.example.demo;

import com.spider.Application;
import com.spider.log.MyLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhuliyang
 * @date 2020-03-08
 * @time 22:54
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class LogTest{
    @Test
    public void contextLoads() {
        MyLog.logInfo("这是info日志...");
        MyLog.logWarn("这是warn日志...");
        MyLog.logError("这是error日志...");
    }
}
