package com.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spider.entity.Area;
import com.spider.entity.HouseInfo;
import com.spider.entity.HousePriceRecord;
import com.spider.log.MyLog;
import com.spider.service.IAreaService;
import com.spider.service.IHouseInfoService;
import com.spider.service.IHousePriceRecordService;
import com.spider.utils.HouseInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 爬取杭州的链家二手房
 *
 * @author zhuliyang
 * @date 2020-03-06
 * @time 22:16
 **/
@Component
public class LianJiaProcessor implements PageProcessor {
    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject("{\"page\":1212}");
        int totalPage = (int) jsonObject.get("totalPage");
        System.out.println(totalPage);
    }
    @Autowired
    private IHouseInfoService houseInfoService;

    @Autowired
    private IHousePriceRecordService housePriceRecordService;

    private String url;

    private Area area;

    private List<HouseInfo> houseInfoList = new ArrayList<>();


    //执行爬虫的方法
    public List<HouseInfo> process() {
        MyLog.logInfo("爬虫" + area.getName() + "地区开始");
        //启动爬虫
        Spider.create(this)
                .addUrl(url)
                .thread(10)//开启5个线程执行任务  开太多要被封
                .run();
        MyLog.logInfo("爬虫" + area.getName() + "地区结束");
        return this.houseInfoList;
    }

    //任务调度的方法
    @Override
    public void process(Page page) {
        //解析页面列表页
        List<Selectable> list = page.getHtml().xpath("//ul[@class='sellListContent']/li[@class='LOGCLICKDATA']").nodes();
        //判断获取到的集合是否为空
        if (list.size() == 0) {
            // 如果为空，表示这是详情页,解析页面，获取详情信息，保存数据
            this.saveHouse(page);

        } else {
            //如果不为空，表示这是列表页,解析出详情页的url地址，放到任务队列中
            for (Selectable selectable : list) {
                //获取url地址
                String jobInfoUrl = selectable.links().toString();
                //把获取到的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            //获取当前页的url
            String curUrl = page.getUrl().toString();
            //获取总页码
            String json = page.getHtml().xpath("//div[@class='page-box house-lst-page-box']/@page-data").toString();
            int totalPage = 0;
            try {
                JSONObject jsonObject = JSON.parseObject(json);
                totalPage = (int) jsonObject.get("totalPage");
            } catch (Exception e) {
                MyLog.logError("获取总页码失败");
            }
            //获取下一页的url
            String nextPageUrl = HouseInfoUtil.getNextPageUrl(curUrl, totalPage);
            if (nextPageUrl != null) { //应该匹配正则表达式 不会写...
                page.addTargetRequest(nextPageUrl);//把下一页url放入任务队列
            }
        }
    }

    //配置参数
    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(20 * 1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3);//设置重试的次数

    @Override
    public Site getSite() {
        return site;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHouseInfoList(List<HouseInfo> houseInfoList) {
        this.houseInfoList = houseInfoList;
    }

    //保存房屋信息 房价变动信息
    @Transactional
    public void saveHouse(Page page) {
        HouseInfo houseInfo = null;
//        HousePriceRecord housePriceRecord = null;
        try {
            houseInfo = HouseInfoUtil.getHouseInfoByPage(page);//把页面属性抽取封装成一个houseInfo
            houseInfo.setAreaId(area.getId());
            houseInfoList.add(houseInfo);
//            //去数据库查询对象
//            HouseInfo houseInDB = houseInfoService.getById(houseInfo.getId());
//            //价格变动信息
//            housePriceRecord = new HousePriceRecord();
//            housePriceRecord.setHouseId(houseInfo.getId());
//            housePriceRecord.setTotalPrice(houseInfo.getTotalPrice());
//            housePriceRecord.setUnitPrice(houseInfo.getUnitPrice());
//            housePriceRecord.setCreateTime(new Date());
//            if (houseInDB == null) {//数据库里没有
//                houseInfoService.save(houseInfo);
//                housePriceRecordService.save(housePriceRecord);
//            } else {
//                //价格发生变化 记录价格变化
//                if (!houseInDB.getTotalPrice().equals(houseInfo.getTotalPrice())) {
//                    housePriceRecordService.save(housePriceRecord);
//                }
//            }
        } catch (Exception e) {
            String houseInfoTOString = houseInfo==null?"null":houseInfo.toString();
//            String housePriceRecordTOString =housePriceRecord==null?"null": housePriceRecord.toString();
            MyLog.logError(e.toString());
            MyLog.logError("page对象属性"+page.toString());
            throw new RuntimeException("保存数据houseInfo:"+houseInfoTOString+"异常 数据回滚");
        }
    }
}
