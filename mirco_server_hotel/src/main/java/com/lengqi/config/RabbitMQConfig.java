package com.lengqi.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //配置rabbitmq的交换机
    @Bean
    public DirectExchange getExchange(){
        return new DirectExchange("hotel_exchange", true, false);
    }
}
