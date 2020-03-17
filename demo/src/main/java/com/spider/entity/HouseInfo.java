package com.spider.entity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author zhuliyang
 * @since 2020-03-05
 */
@Data
public class HouseInfo {

    private static final long serialVersionUID = 1L;
    @TableId
    private String id;
    /**
     * 链接
     */
    private String link;

    /**
     * 地区id
     */
    @TableField("areaId")
    private Integer areaId;

    /**
     * 所在区域名称
     */
    @TableField("districtName")
    private String districtName;

    /**
     * 所在街道名称
     */
    @TableField("streetName")
    private String streetName;

    /**
     * 小区名称
     */
    @TableField("communityName")
    private String communityName;

    /**
     * 地铁信息
     */
    @TableField("subwayInfo")
    private String subwayInfo;

    /**
     * 经纬度的geoHash
     */
    @TableField("geoHash")
    private String geoHash;

    /**
     * 总价
     */
    @TableField("totalPrice")
    private Double totalPrice;

    /**
     * 单价
     */
    @TableField("unitPrice")
    private Double unitPrice;

    /**
     * 房屋户型
     */
    @TableField("houseType")
    private String houseType;

    /**
     * 建筑面积
     */
    private Double area;

    /**
     * 所在楼层
     */
    @TableField("houseLevel")
    private String houseLevel;

    /**
     * 装修情况
     */
    private String decoration;

    /**
     * 房屋朝向
     */
    private String forword;

    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;

    @TableField("isDelete")
    private Integer isDelete;


}
