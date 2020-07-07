package com.lengqi.feign;

import com.lengqi.entity.City;
import com.lengqi.entity.District;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("MIRCO-CITY")

public interface CityFeign {
    @RequestMapping("city/list")
    List<City> queryCityList();
    @RequestMapping("city/insert")
    /**
     * 对象不能通过参数传递，@RequestBody表示将对象放入请求体，所以feign传递参数只能有一个@requestBody
     * 如果想传递两个对象，可以将对象放到map，然后将map前面加@RequestBody
     * 另外，如果传递的数据特别大，可以考虑将数据写入redis中读取
     */
    int insertCity(@RequestBody City city);
    @RequestMapping("city/updateCityHotelNumber")
    int updateCityHotelNumber(@RequestParam("cid") Integer cid, @RequestParam("number") Integer number);
    @GetMapping("city/queryByCityId")
    City queryByCityId(@RequestParam("id") Integer id);

    /**
     * district
     */
    @RequestMapping("district/insertDist")
     int insertDist(@RequestBody District district);
    @RequestMapping("district/getAll")
     List<District> getAll();
    @RequestMapping("/queryById")
     District queryById(Integer cid);
}
