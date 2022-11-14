package com.example.service.impl;

import com.example.pojo.Product;
import com.example.service.FeignOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
public class FeignOrderServiceImpl implements FeignOrderService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> selectProductListByLoadBalancerAnnotation() {
        String url = "http://product-service/product/list";
        log.info("订单服务调用商品服务...");
        log.info("从注册中心获取到的商品服务地址为：{}", url);
        // ResponseEntity: 封装了返回数据
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                url,
                GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {});
        log.info("商品信息查询结果为：{}", response.getBody().toString());
        return response.getBody();
    }

}