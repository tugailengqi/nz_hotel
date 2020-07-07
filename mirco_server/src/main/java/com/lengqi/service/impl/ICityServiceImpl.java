package com.lengqi.service.impl;

import com.lengqi.entity.City;
import com.lengqi.mapper.CityMapper;
import com.lengqi.service.ICityService;
import com.lengqi.util.PinyingUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ICityServiceImpl implements ICityService {
    @Resource
    private CityMapper cityMapper;

    /**
     * 查询城市列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<City> list() {
        return cityMapper.selectList(null);
    }

    /**
     * 添加城市
     * @param city
     * @return
     */
    @Override
    @Transactional
    public int insert(City city) {
        //通过城市的名称，生成城市的拼音
        city.setCityPinyin(PinyingUtil.str2Pinyin(city.getCityName()));
        return cityMapper.insert(city);
    }

    /**
     * 修改城市酒店数量
     * id为cid的城市，
     * 酒店数量增加number
     *
     * 思考？这个方法的逻辑有没有问题？
     * 线程安全
     *
     *
     * @param cid
     * @param number
     * @return
     */
    @Override
    @Transactional
    public int update(Integer cid, Integer number) {
        return cityMapper.updateHotelNumber(cid,number);
    }

    @Override
    @Transactional(readOnly = true)
    public City queryById(Integer id) {
        return cityMapper.selectById(id);
    }
}
