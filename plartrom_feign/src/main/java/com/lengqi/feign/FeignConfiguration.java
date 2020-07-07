package com.lengqi.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 所有配置到启动类的@Bean，Enablexxx
 * 都可以配置到@Configuration这个注解类中，可以看成启动类
 */
@Configuration
@EnableFeignClients("com.lengqi.feign")
public class FeignConfiguration {
}
