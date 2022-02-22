package com.spider.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 土狗信息
 * </p>
 *
 * @author zhuliyang
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CurrencyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String token;

    private String name;

    private Integer holder;

    private LocalDateTime createTime;

    private Integer liquidity;

    private LocalDateTime publishTime;


}
