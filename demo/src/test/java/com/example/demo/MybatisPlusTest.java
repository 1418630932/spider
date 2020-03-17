package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.Application;
import com.spider.entity.Area;
import com.spider.entity.HouseInfo;
import com.spider.mapper.HouseInfoMapper;
import com.spider.service.IAreaService;
import com.spider.service.IHouseInfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author zhuliyang
 * @date 2020-03-07
 * @time 23:35
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class MybatisPlusTest {
    @Autowired
    private IHouseInfoService houseInfoService;

    @Autowired
    private IAreaService iAreaService;

    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Test
    public void test(){
//        HouseInfo houseInfo = new HouseInfo();
//        houseInfo.setId("122");
//        houseInfo.setLink("12121212");
//        houseInfo.setAreaId(1);
//        houseInfo.setCreateTime(new Date());
//        houseInfoService.save(houseInfo);
//        houseInfoService.removeById("17");
    }


    @Test
    public void test3(){
        String areaCode = "hz";
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",areaCode );
        Area one = iAreaService.getOne(queryWrapper);
        System.out.println(one);
    }

    @Test
    public void test4(){
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", 2);
        List<Area> areaList = iAreaService.list(queryWrapper);
        System.out.println(areaList);
    }

    @Test
    public void test5(){
        List<String> idListByAreaId = houseInfoMapper.getIdListByAreaId(151);
        System.out.println(idListByAreaId.size());
    }

}
