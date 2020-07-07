package com.lengqi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengqi.entity.City;
import org.apache.ibatis.annotations.Param;

public interface CityMapper extends BaseMapper<City> {
    int updateHotelNumber(@Param("cid") int cid,@Param("number") int number);
}
