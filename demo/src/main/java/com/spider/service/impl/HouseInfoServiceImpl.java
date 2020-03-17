package com.spider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spider.entity.HouseInfo;
import com.spider.mapper.HouseInfoMapper;
import com.spider.service.IHouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuliyang
 * @since 2020-03-05
 */
@Service
public class HouseInfoServiceImpl extends ServiceImpl<HouseInfoMapper, HouseInfo> implements IHouseInfoService {
    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Override
    public List<HouseInfo> distinctList(int areaId, List<HouseInfo> list) {
        List<String> idList= houseInfoMapper.getIdListByAreaId(areaId);
        Set<String> set = new HashSet<>(idList);
        List<HouseInfo> res = new ArrayList<>();
        //对list进行筛选  筛选出不存在于数据库中的数据
        for (HouseInfo houseInfo : list) {
            if (!set.contains(houseInfo.getId())){
                res.add(houseInfo);
            }
        }
        return res;
    }
}
