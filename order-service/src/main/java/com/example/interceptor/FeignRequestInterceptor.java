package com.example.interceptor;

import com.example.service.redis.JwtService;
import com.example.util.SpringContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  用于实现令牌信息中继  
 */
 @Component
 public class FeignRequestInterceptor implements RequestInterceptor {
     @Autowired
    private JwtService jwtService;
     private static FeignRequestInterceptor interceptor;
    private final String FROM = "FromAAAABC";

    @PostConstruct
    public void init() {
        interceptor = this;
        interceptor.jwtService = this.jwtService;
    }
    @Override
    public void apply(RequestTemplate template) {
        System.out.println("Request Template executed");
        template.header("from", FROM);

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取 RequestContextHolder 中的信息
        Map<String, String> headers = getHeaders(requestAttributes.getRequest());
        //放入 feign 的 RequestTemplate 中
            String jwt = headers.get("authorization");
            boolean jwtValid = interceptor.jwtService.checkJwt(jwt);

            if (jwtValid) {
            template.removeHeader("authorization");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                template.header(entry.getKey(), entry.getValue()); // .replace("authorization ", "")
            }
        } else {
            template.header("token", "null");
        }
    }

    /**
     * 获取原请求头
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }


    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}