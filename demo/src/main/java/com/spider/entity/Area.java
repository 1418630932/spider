package com.spider.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuliyang
 * @since 2020-03-05
 */
@Data
public class Area {

    private static final long serialVersionUID = 1L;
    @TableId
    private int id;

    private String name;

    private String code;

    private String link;

    @TableField("parentsId")
    private Integer parentsId;

    private Integer level;


}
