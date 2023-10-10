package com.hmapi.config;

import com.hmapi.interceptor.UserInfointerceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignLogLevelConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
    @Bean
    public RequestInterceptor userInfoInterceptor(){
        return new UserInfointerceptor();
    }
}
