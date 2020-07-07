package com.lengqi.feign;

import com.lengqi.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("MIRCO-HOTEL")
public interface HotelFeign {
    @RequestMapping("hotel/insert")
    int insertHotel(@RequestBody Hotel hotel);
    @RequestMapping("hotel/list")
    List<Hotel> queryAll();
}
