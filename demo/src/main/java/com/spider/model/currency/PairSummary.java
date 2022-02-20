package com.spider.model.currency;

import lombok.Data;

/**
 * @author zhuliyang
 * @date 2021-07-26
 * @time 23:07
 **/
@Data
public class PairSummary{
    //对子地址
    private String address;
    //当前价格
    private Double price;
    //24小时之前价格
    private Double price24h;
    //24小时交易量
    private Double volume24;

}
