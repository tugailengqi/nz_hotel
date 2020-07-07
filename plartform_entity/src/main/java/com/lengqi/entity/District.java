package com.lengqi.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class District implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer cid;

    private String district;

    private Double lon;

    private Double lat;

    private Integer hotalNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Byte status;
}