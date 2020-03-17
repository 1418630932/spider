package com.spider.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spider.entity.HouseInfo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhuliyang
 * @since 2020-03-05
 */
public interface HouseInfoMapper extends BaseMapper<HouseInfo> {
    @Select("select id from house_info where isDelete=0 and areaId = #{areaId}")
    List<String> getIdListByAreaId(int areaId);
}
