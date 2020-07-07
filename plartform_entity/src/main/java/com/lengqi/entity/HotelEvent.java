package com.lengqi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HotelEvent implements Serializable {
    private  Integer hid;
    //1 - 点击率 ，2-收藏量 3
    private Integer event;

}
