package com.lengqi.service;

import com.lengqi.entity.Hotel;

import java.util.List;

public interface HotelService {
    int saveHotel(Hotel hotel);
    List<Hotel> list();
    Hotel getById(Integer hid);
}
