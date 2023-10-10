package com.hmgeteway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@EnableUserinterceptor
//@EnableAutoConfiguration(exclude = WebMvcConfigurer.class)
public class App 
{
    public static void main( String[] args ){
        SpringApplication.run(App.class,args);
    }
}
