package com.lengqi.controller;

import com.lengqi.entity.City;
import com.lengqi.entity.ResultData;
import com.lengqi.service.ICityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/web")
public class WebController {
    @Resource
    private ICityService iCityService;
    @RequestMapping("/cityList")
    public ResultData<List<City>> cityList(){
        List<City> list = iCityService.list();
        return new ResultData<List<City>>().setCode(ResultData.Code.CODE_SUCC).setData(list);
    }
}
