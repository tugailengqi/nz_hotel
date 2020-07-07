package com.lengqi.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//链式编程
public class City {
    //标记主键，并且设置自动增长
    //标记主键，便于主键回填
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String cityName;

    private String cityPinyin;

    private Integer hotalNumber;

    private Date createTime;

    private Byte status;
}