package com.spider.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 土狗信息汇总
 * </p>
 *
 * @author zhuliyang
 * @since 2021-07-26
 */
@Data
public class CurrencySummaryDto {
    private String id;

    private String token;

    private String name;

    private Integer holder;

    private LocalDateTime createTime;
    //池子大小($)
    private Integer liquidity;
    //24小时波动幅度（%）
    private String change24H;


}
