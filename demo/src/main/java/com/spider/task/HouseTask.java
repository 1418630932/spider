package com.spider.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.entity.Area;
import com.spider.entity.HouseInfo;
import com.spider.log.MyLog;
import com.spider.processor.LianJiaProcessor;
import com.spider.service.IAreaService;
import com.spider.service.IHouseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HouseTask {
    @Autowired
    private LianJiaProcessor lianJiaProcessor;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IHouseInfoService houseInfoService;

    //爬取数据库表中每个地区的二手房信息
//    @Scheduled(cron = "0 0 12 * * ?")    //每天中午12点启动一次   凌晨要被封
    @Scheduled(initialDelay = 1000, fixedDelay = 24*60*6000) //容器启动后10秒开始执行 然后隔1天再执行
    public void start() {
        MyLog.logInfo("开始爬虫");
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", 4);//查询4级地区
        List<Area> areaList = areaService.list(queryWrapper);
        Collections.shuffle(areaList);//打乱集合 如果老是按照一个顺序爬 后面的数据大概率被反爬虫 会失败
        for (Area area : areaList) {
            lianJiaProcessor.setUrl(area.getLink());
            lianJiaProcessor.setArea(area);
            lianJiaProcessor.setHouseInfoList(new ArrayList<>());
            List<HouseInfo> spiderList = lianJiaProcessor.process();//启动爬虫
            //筛选爬下来的数据
            List<HouseInfo> distinctList = houseInfoService.distinctList(area.getId(), spiderList);
            try {
                //批量保存
                houseInfoService.saveBatch(distinctList);
            } catch (Exception e) {
                MyLog.logError(area.getName()+"地区数据批量保存失败");
            }
        }
        MyLog.logInfo("结束爬虫");
    }
}
