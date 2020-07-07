package com.lengqi.controller;

import com.lengqi.entity.District;
import com.lengqi.service.DistrictService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("district")
public class DistrictController {
    @Resource
    private DistrictService districtService;
    @RequestMapping("/insertDist")
    public int insertDist(@RequestBody District district){
        return districtService.insertDistrict(district);
    }
    @RequestMapping("/getAll")
    public List<District> getAll(){
        return districtService.getList();
    }
    @RequestMapping("/queryById")
    public District queryById(Integer cid){
        return districtService.getById(cid);
    }
}
