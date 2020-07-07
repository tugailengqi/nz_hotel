package com.lengqi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lengqi.mapper")
@EnableTransactionManagement
public class CityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityApplication.class,args);
    }
}
