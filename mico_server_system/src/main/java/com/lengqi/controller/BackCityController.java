package com.lengqi.controller;

import com.lengqi.entity.City;
import com.lengqi.feign.CityFeign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/back")
public class BackCityController {
    @Resource
    private CityFeign cityFeign;
    @RequestMapping("/toCityAdd")
    public String toCityAdd(){
        return "addcity";
    }
    @RequestMapping("/cityAdd")
    public String cityAdd(City city){
        cityFeign.insertCity(city);
        return "redirect:/back/cityList";
    }
    @RequestMapping("/cityList")
    public String cityList(Model model){
        List<City> cities = cityFeign.queryCityList();
        model.addAttribute("cities",cities);
        return "cityList";
    }

}
