package com.lengqi.service.impl;

import com.lengqi.entity.Hotel;
import com.lengqi.feign.CityFeign;
import com.lengqi.mapper.HotelMapper;
import com.lengqi.service.HotelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class HotelServiceImpl implements HotelService {
    @Resource
    private HotelMapper hotelMapper;
//    @Resource
//    private CityFeign cityFeign;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    @Transactional
    public int saveHotel(Hotel hotel) {
//        同步修改城市酒店和区域酒店数量
//        cityFeign.updateCityHotelNumber(hotel.getCid(),1);
        //通常发布信息之前，需要保证业务正常才发送消息。
        int flag = hotelMapper.insert(hotel);
        //发布rabbit
        rabbitTemplate.convertAndSend("hotel_exchange","add",hotel);
        return flag;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> list() {
        return hotelMapper.selectList(null);
    }

    @Override
    @Transactional
    public Hotel getById(Integer hid) {
        return hotelMapper.selectById(hid);
    }
}
