package com.example.service;

import com.example.interceptor.FeignConfiguration;
import com.example.pojo.Order;
import com.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@FeignClient(name="product-service", configuration = FeignConfiguration.class)
public interface FeignOrderService {

    //Order selectOrderById(Integer id);
    @RequestMapping(value="/product/list", method= RequestMethod.GET)
    List<Product> selectProductListByLoadBalancerAnnotation();
}
