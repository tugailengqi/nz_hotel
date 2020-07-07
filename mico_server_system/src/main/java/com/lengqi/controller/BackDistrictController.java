package com.lengqi.controller;

import com.lengqi.entity.City;
import com.lengqi.entity.District;
import com.lengqi.feign.CityFeign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/back")
public class BackDistrictController {
    @Resource
    CityFeign cityFeign;
    @GetMapping("/toDistrict")
    public String toDistrict(@RequestParam("id") Integer id, Model model){
        City city = cityFeign.queryByCityId(id);
        model.addAttribute("city",city);
        return "districtList";
    }
    @RequestMapping("/addDistrict")
    public String addDistrict(District district){
        cityFeign.insertDist(district);
        return "redirect:/back/districtList";
    }
    @RequestMapping("getAll")
    public String getAll(Model model){
        List<District> districts = cityFeign.getAll();
        model.addAttribute("districts",districts);
        return "districtList";
    }
}
