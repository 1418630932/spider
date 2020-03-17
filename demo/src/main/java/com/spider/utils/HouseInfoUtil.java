package com.spider.utils;

import com.spider.entity.HouseInfo;
import com.spider.log.MyLog;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author zhuliyang
 * @date 2020-03-07
 * @time 0:48
 **/
public class HouseInfoUtil {
//    public static void main(String[] args) {
//        System.out.println(getNextPageUrl("https://hz.lianjia.com/ershoufang/98"));
//    }
    //获取url中的id 正则匹配
    //https://hz.lianjia.com/ershoufang/103106344842.html ===>103106344842
    public static String getIdByUrl(String url) {
        if (StringUtils.isEmpty(url)) return null;
        String regEx = "\\D";
        return  url.replaceAll(regEx, "");
    }

    //获取下一页的url
    // https://hz.lianjia.com/ershoufang/pg1  ===>https://hz.lianjia.com/ershoufang/pg2
    public static String getNextPageUrl(String url,int totalPage) {
        if (totalPage<=1)return null;
        int lastIndex = url.lastIndexOf("pg");
        if (lastIndex==-1)return null;
        String numberStr =   url.substring(lastIndex+2);
        int pageNum = Integer.parseInt(numberStr);
        //返回下一页链接
        return pageNum < totalPage ? url.substring(0, lastIndex + 2) + (pageNum + 1) : null;
    }

    //获取url中的地区code
    //https://hz.lianjia.com/ershoufang/pg1 ====>hz
    public static String getAreaCode(String url) {
        if (StringUtils.isEmpty(url)) return null;
        int start = url.indexOf("/");
        int end = url.indexOf(".");
        return url.substring(start + 2, end);
    }

    //根据页面封装houseInfo
    public static HouseInfo getHouseInfoByPage(Page page) {
        if (page == null) return null;
        HouseInfo houseInfo = new HouseInfo();
        String id = HouseInfoUtil.getIdByUrl(page.getUrl().toString());
        String link = page.getUrl().toString();
        String districtName = page.getHtml().xpath("//div[@class='areaName']/span[@class='info']/a[1]/text()").toString();
        String streetName = page.getHtml().xpath("//div[@class='areaName']/span[@class='info']/a[2]/text()").toString();
        String subway = page.getHtml().xpath("//div[@class='areaName']/a/text()").toString();
        String communityName = page.getHtml().xpath("//div[@class='communityName']/a[1]/text()").toString();
        String totalPrice = page.getHtml().xpath("//div[@class='price']/span[1]/text()").toString();
        String unitPrice = StringUtils.strip(StringUtils.strip(page.getHtml().xpath("//div[@class='unitPrice']/span[1]/text()").toString(), "单价"), "元/平米");
        List<Selectable> baseInfo = page.getHtml().xpath("//div[@class='introContent'][1]/div[@class='base']//div[@class='content']/ul/li").nodes();
        String houseType = baseInfo.get(0).xpath("//li/text()").toString();
        String houseLevel = baseInfo.get(1).xpath("//li/text()").toString();
        String area = baseInfo.get(2).xpath("//li/text()").toString();
        String decoration = baseInfo.get(8).xpath("//li/text()").toString();
        String forword = baseInfo.get(6).xpath("//li/text()").toString();
        houseInfo.setId(id);
        houseInfo.setLink(link);
        houseInfo.setDistrictName(districtName);
        houseInfo.setStreetName(streetName);
        houseInfo.setSubwayInfo(subway);
        houseInfo.setCommunityName(communityName);
        houseInfo.setTotalPrice(Double.parseDouble(totalPrice));
        houseInfo.setUnitPrice(Double.parseDouble(unitPrice));
        houseInfo.setHouseType(houseType);
        houseInfo.setHouseLevel(houseLevel);
        houseInfo.setArea(Double.parseDouble(area.substring(0, area.length() - 1)));
        houseInfo.setDecoration(decoration);
        houseInfo.setForword(forword);
        houseInfo.setCreateTime(new Date());
        return houseInfo;
    }

}
