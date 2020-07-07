package com.lengqi.listener;

import com.lengqi.entity.Hotel;
import com.lengqi.entity.HotelEvent;
import com.lengqi.service.ISearchService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@RabbitListener(bindings = {
        @QueueBinding(exchange = @Exchange(name = "hotel_exchange",type = "direct"),
                value = @Queue(name = "search_queue",durable = "true"),key = {"add","update"})
})
public class RabbitMsgListener {
    @Resource
    private ISearchService searchService;
    /**
     * 保证MQ的消费方法幂等
     * @param hotel
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void msgHandler(Hotel hotel, Channel channel, Message message){
        try {
            //接收消息，保存es
            searchService.addDocument(hotel);
            //手动处理消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理酒店的各种事件
     * @param hotelEvent
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void msgHandler(HotelEvent hotelEvent,Channel channel,Message message){
        try {
            //接收消息，保存es
            System.out.println("当前接收到酒店的事件对象："+hotelEvent);
            searchService.updateDoc(hotelEvent);
            //手动处理消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
