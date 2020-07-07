package com.lengqi.service;

import com.lengqi.entity.City;

import java.util.List;

public interface ICityService {
    List<City> list();
    int insert(City city);
    int update(Integer cid,Integer number);
    City queryById(Integer id);
}
