package com.spider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spider.entity.HouseInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuliyang
 * @since 2020-03-05
 */
public interface IHouseInfoService extends IService<HouseInfo> {
    /**
     * 去除list中已经在数据库中存在的房源
     * @param areaId  地区id
     * @param list     需要清洗的数据
     */
    List<HouseInfo> distinctList(int areaId, List<HouseInfo> list);
}
