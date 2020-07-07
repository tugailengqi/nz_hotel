package com.lengqi.controller;

import com.lengqi.entity.City;
import com.lengqi.service.ICityService;
import com.lengqi.service.impl.ICityServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 其他其他微服务访问的接口
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Resource
    private ICityService cityService;
    /**
     * 查询城市列表
     * @return
     */
    @RequestMapping("/list")
    public List<City> queryCityList(){
        System.out.println("查询了全部");
        return cityService.list();
    }

    /**
     * 添加城市
     * @return
     */
    @RequestMapping("/insert")
    public int insertCity(@RequestBody City city){
        return cityService.insert(city);
    }

    /**
     * 修改城市的酒店数量
     * @return
     */
    @RequestMapping("/updateCityHotelNumber")
    public int updateCityHotelNumber(@RequestParam("cid") Integer cid,@RequestParam("number") Integer number){
        return cityService.update(cid, number);
    }

    /**
     * 根据id查询某个城市
     */
    @GetMapping("/queryByCityId")
    public City queryByCityId( @RequestParam("id") Integer id){
        return cityService.queryById(id);
    }

}
