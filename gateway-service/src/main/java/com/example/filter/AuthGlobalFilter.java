package com.example.filter;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.example.service.redis.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.util.Constants.API;

/**
 * 统一redirect到网关，网关不通过的报错。登录放行。
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Autowired
    private JwtService jwtService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //鉴权
        List<String> name = exchange.getRequest().getHeaders().get("name");
        log.info("name：{}",name);
        String path = exchange.getRequest().getURI().getPath();
        //path：/a/helloword
        log.info("path：{}",path);
        String url = exchange.getRequest().getURI().toString();
        //url：http://localhost:8082/a/helloword
        log.info("url：{}",url);
        if (!CollectionUtils.isEmpty(name)) {
            return Mono.error(new Exception("100,当前未登录"));
        }
        if (url.contains("/login") || url.contains(API)) {
            ServerHttpRequest httpRequest = exchange.getRequest().mutate().header("from", "fromAAA".toString()).build();
            return chain.filter(exchange.mutate().request(httpRequest.mutate().build()).build());
        }
        List<String> jwts = exchange.getRequest().getHeaders().get("authorization");
        System.out.println(jwts);
        if (CollectionUtils.isEmpty(jwts)) {
            return Mono.error(new Exception("100,当前未登录"));
        }
        if (jwtService.checkJwt(jwts.get(0))) {
                ServerHttpRequest httpRequest = exchange.getRequest().mutate().header("from", "fromAAA".toString()).build();
                return chain.filter(exchange.mutate().request(httpRequest.mutate().build()).build());
            }

        //return returnsToken(serverHttpResponse, authToken);

        return chain.filter(exchange);

    }
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}