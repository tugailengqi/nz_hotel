package com.lengqi.controller;

import com.lengqi.aop.IsLogin;
import com.lengqi.aop.LoginParams;
import com.lengqi.entity.Hotel;
import com.lengqi.entity.HotelEvent;
import com.lengqi.entity.ResultData;
import com.lengqi.service.HotelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Resource
    private HotelService hotelService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @RequestMapping("/insert")
    public int insertHotel(@RequestBody Hotel hotel){
        return hotelService.saveHotel(hotel);
    }
    @RequestMapping("/list")
    public List<Hotel> queryAll(){
        return hotelService.list();
    }

    /**
     * 根据id查询酒店详情
     * @param hid
     * @return
     */
    @RequestMapping("/info")
    public ResultData<Hotel> queryHotel(Integer hid){
        //除了查询之外，相当于进行了一次点击
        HotelEvent hotelEvent = new HotelEvent().setHid(hid).setEvent(1);
        System.out.println(hotelEvent);
        //发送消息给search服务,这样发消息的话，城市队列也会收到消息，但我们只要搜索服务收到即可
        rabbitTemplate.convertAndSend("hotel_exchange","update",hotelEvent);
        Hotel hotel = hotelService.getById(hid);
        return new ResultData<Hotel>().setCode(ResultData.Code.CODE_SUCC).setData(hotel);
    }

    /**
     * 收藏酒店
     * jwtToken 从客户端拿到，因为登录的时候存在客户端
     * 1、完全无需登录可以处理的业务
     * 2、可以登录也可以不登录处理的业务，登录和不登录处理的方式不同
     * 3、必须要登录才能处理的业务，比如，订单、支付
     * @return
     */

    @RequestMapping("/soucang")
    @IsLogin(mustlogin = true)
    public ResultData<Hotel> soucangHotel(Integer hid){
        System.out.println("酒店id：" + hid);
        System.out.println("拿到对象："+ LoginParams.getUser());
        return null;
    }
}
