package com.example.interceptor;

import com.example.service.redis.JwtService;
import com.example.util.Constants;
import com.example.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/*
@author chenhao
@data 2020/6/9 - 18:35
*/
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtService jwtService;
    private static MyInterceptor interceptor;

    @PostConstruct
    public void init() {
        interceptor = this;
        interceptor.jwtService = this.jwtService;
    }
    // = SpringContextUtil.getContext().getBean(JwtService.class);
    //return true;执行下一个拦截器，放行。
    //return false;不执行下一个拦截器。
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=====处理前=====");
        boolean jwtValid = false;
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        if (request.getRequestURI().contains("swagger-ui") || request.getRequestURI().contains(Constants.API)) {
            //jwtValid = true;
            return true;
        } else {
            jwtValid = interceptor.jwtService.checkJwt(request.getHeader("authorization"));
        }
        String from = request.getHeader("from");
        if (!jwtValid || !("fromAAA".equals(from)|| "FromAAAABC".equals(from))) {
            //response.sendRedirect("http://127.0.0.1:80/a?i=1");
            PrintWriter writer = null;
            try {

                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.setContentType("application/json; charset=utf-8");
                String s = "访问失败!";
                //TODO 一定要在response设置后再getWriter() 否则乱码，提前设置全部无效
                writer = response.getWriter();
                writer.append(s);
                writer.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (writer != null){
                    writer.close();
                }
            }
            return false;
        }
        return true;
    }

    //拦截日志
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("=====处理中=====");

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}