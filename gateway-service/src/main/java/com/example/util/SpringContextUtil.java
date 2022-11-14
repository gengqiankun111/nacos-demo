package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Classname SpringContextUtil
 * @Description 此工具类用于从Spring的上下文中去获取到类
 * 调用的时候例如：private CommonService commonService = SpringContextUtil.getContext().getBean(CommonService.class);
 * @Date 2019/11/4 2:00 PM
 * @Created by wangdong
 */
@Component
public final class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}