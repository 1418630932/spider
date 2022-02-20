package com.spider.model.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhuliyang
 * @date 2021-07-26
 * @time 23:07
 **/
@Data
public class Currency {
    private String id;
    private Long createdAtTimestamp;
    private Token token0;
    private Token token1;
    private Info info;
}
