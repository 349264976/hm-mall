package com.hmall.cart;

import com.hmall.common.annotation.EnableUserinterceptor;
import com.hmapi.client.ItemClient;
import com.hmapi.config.FeignLogLevelConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@EnableFeignClients(basePackages = "com.hmapi.client",defaultConfiguration = FeignLogLevelConfig.class)
@MapperScan("com.hmall.cart.mapper")
@SpringBootApplication
@EnableUserinterceptor
public class App 
{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}