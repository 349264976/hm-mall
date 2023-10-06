package com.hmtrade;

import com.hmapi.config.FeignLogLevelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@EnableFeignClients(basePackages = "com.hmapi.client",defaultConfiguration = FeignLogLevelConfig.class)
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
