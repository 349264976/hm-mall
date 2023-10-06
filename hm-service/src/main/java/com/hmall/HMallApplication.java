package com.hmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@MapperScan("com.hmall.mapper")
@SpringBootApplication
@EnableSwagger2WebMvc
public class HMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(HMallApplication.class, args);
    }
}