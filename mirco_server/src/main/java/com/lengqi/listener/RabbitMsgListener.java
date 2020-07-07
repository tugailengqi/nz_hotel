package com.lengqi.listener;

import com.lengqi.entity.Hotel;
import com.lengqi.service.ICityService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class RabbitMsgListener {
    @Resource
    private ICityService cityService;
    /**
     * 保证MQ的消费方法幂等
     * @param hotel
     * @param channel
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(exchange = @Exchange(name = "hotel_exchange",type = "direct"),
            value = @Queue(name = "city_queue",durable = "true"),
            key = {"add"})
    })
    public void msgHandler(Hotel hotel, Channel channel, Message message){
        System.out.println("城市服务接收对象"+hotel);
        //修改城市数量
        cityService.update(hotel.getCid(),1);
        //消息手动确认
        try {

            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
