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
public class HousePriceRecord {

    private static final long serialVersionUID = 1L;
    @TableId
    private int id;
    @TableField("houseId")
    private String houseId;

    /**
     * 总价
     */
    @TableField("totalPrice")
    private Double totalPrice;
    /**
     * 每平方价格
     */
    @TableField("unitPrice")
    private Double unitPrice;

    @TableField("isDelete")
    private Integer isDelete;

    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;


}
