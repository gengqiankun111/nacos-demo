package com.example.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/** * Feign调用的时候添加请求头from */
//@Configuration
    @Deprecated
public class FeignConfiguration implements RequestInterceptor {
    private final String FROM = "FromAAAABC";
    @Override
    public void apply(RequestTemplate requestTemplate) {;
        System.out.println("FeignConfiguration FromAAAABC");
        requestTemplate.header("from", FROM).header("Authorization", "Bearer " + ""); // from Redis or final value .header("Authorization", "Bearer " + "")
    }
}