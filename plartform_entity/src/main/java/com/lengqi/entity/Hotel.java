package com.lengqi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Hotel implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String hotalName;
    private String hotalImage;
    private Integer type;
    private String hotalInfo;
    private String keyword;
    @JSONField(serialize = false)
    private Double lon;
    @JSONField(serialize = false)
    private Double lat;

    //经纬度坐标
    @TableField(exist = false)
    private Double[] location = new Double[2];

    private Integer star;
    private String brand;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date openTime;
    @JSONField(serialize = false)
    private Integer cid;
    private String regid;
    private BigDecimal price;
    private Integer roomNumber;

    /**
     * 城市名称
     */
    @TableField(exist = false)
    private String cityname;

    //酒店的点击率
    @TableField(exist = false)
    private Integer djl = 0;
    //酒店的关注量
    @TableField(exist = false)
    private Integer gzl = 0;
    //下单量
    //评论数（好评、中评、差评）

    @TableField(exist = false)
    private String juli;

    public Hotel setLon(Double lon) {
        this.lon = lon;
        location[0] = lon;
        return this;
    }

    public Hotel setLat(Double lat) {
        this.lat = lat;
        location[1] = lat;
        return this;
    }


    public void setLocation(Double[] location) {
        this.location = location;
        this.lon = location[0];
        this.lat = location[1];
    }
}