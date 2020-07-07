package com.lengqi.service;

import com.lengqi.entity.District;

import java.util.List;

public interface DistrictService {
    int insertDistrict(District district);
    List<District> getList();
    District getById(Integer cid);
//    int updateNumber(Integer cid);
}
