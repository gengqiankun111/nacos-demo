package com.example;

import com.example.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;


@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableOpenApi
public class GatewayServiceApplication {

//    @Bean
//    @LoadBalanced // 负载均衡注解
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    public static void main(String[] args) {
        System.out.println(StringUtil.getSdf());
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}