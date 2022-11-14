package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

// 开启 @EnableDiscoveryClient 注解，当前版本默认会开启该注解
//@EnableDiscoveryClient
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableRedisHttpSession
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}