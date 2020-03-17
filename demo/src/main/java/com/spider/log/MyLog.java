package com.spider.log;

import com.spider.processor.LianJiaProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhuliyang
 * @date 2020-03-13
 * @time 13:07
 **/
public class MyLog {
    private static Logger logger = LoggerFactory.getLogger(MyLog.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarn(String message) {
        logger.warn(message);
    }

    public static void logError(String message) {
        logger.error(message);
    }


}
