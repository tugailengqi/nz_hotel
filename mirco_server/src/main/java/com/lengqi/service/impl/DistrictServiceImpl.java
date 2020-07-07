package com.lengqi.service.impl;

import com.lengqi.entity.District;
import com.lengqi.mapper.DistrictMapper;
import com.lengqi.service.DistrictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class DistrictServiceImpl implements DistrictService {
    @Resource
    private DistrictMapper districtMapper;
    @Override
    @Transactional
    public int insertDistrict(District district) {
        return districtMapper.insert(district);
    }

    @Override
    @Transactional(readOnly = true)
    public List<District> getList() {
        return districtMapper.selectList(null);
    }

    @Override
    @Transactional(readOnly = true)
    public District getById(Integer cid) {
        return districtMapper.selectById(cid);
    }
}
