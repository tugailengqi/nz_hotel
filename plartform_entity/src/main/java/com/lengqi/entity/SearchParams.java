package com.lengqi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SearchParams implements Serializable {
    //关键字
    private String keyword;
    //最低价
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String cityname;
    private Double lat;
    private Double lon;
    //排序类型：1 - 智能排序 2 - 价格升序 3 - 距离排序
    private Integer sortType = 1;
}
