//package com.example.demo;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.spider.Application;
//import com.spider.entity.Area;
//import com.spider.entity.HouseInfo;
//import com.spider.processor.LianJiaProcessor;
//import com.spider.service.IAreaService;
//import com.spider.service.IHouseInfoService;
//import com.spider.utils.HouseInfoUtil;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//class WebMagicDemoApplicationTests {
//
//
//
//    @Test
//    public void test() {
////        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
////        queryWrapper.eq("name", "淳安");
////        queryWrapper.eq("level", 4);
////        Area area = areaService.getOne(queryWrapper);
////        lianJiaProcessor.setArea(area);
////        lianJiaProcessor.setUrl(area.getLink());
////        lianJiaProcessor.process();
//    }
//
//
////    @Test
////    public void start() {
////        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
////        queryWrapper.eq("level", 2);
////        List<Area> areaList = areaService.list(queryWrapper);
////        for (Area area : areaList) {
////            String url = PREFIX+area.getCode()+SUFFIX;
////            lianJiaProcessor.setUrl(url);
////            lianJiaProcessor.setArea(area);
////            lianJiaProcessor.process();//启动爬虫
////        }
////    }
//}
