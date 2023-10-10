package com.hamll.item;

import com.hmapi.config.FeignLogLevelConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@MapperScan("com.hamll.item.mapper")
@EnableFeignClients(basePackages = "com.hmapi.client",defaultConfiguration = FeignLogLevelConfig.class)
@SpringBootApplication
public class App 
{
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);

    }
}
