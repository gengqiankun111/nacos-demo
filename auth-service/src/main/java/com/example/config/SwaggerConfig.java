package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @author: shf description: date: 2022/3/28 11:35
 */
@Component
@EnableOpenApi
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfig {

 /**
  * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
  */
 private Boolean enable= true;

 /**
  * 项目应用名
  */
 private String applicationName = "abc";

 /**
  * 项目版本信息
  */
 private String applicationVersion = "1.11";

 /**
  * 项目描述信息
  */
 private String applicationDescription = "desc";

 @Bean
 public Docket docket() {
  List<SecurityContext> securityContexts = Collections.singletonList(SecurityContext.builder()
          .securityReferences(Collections.singletonList(SecurityReference.builder()
                  .reference("Authorization")
                  .scopes(new AuthorizationScope[]{new AuthorizationScope("global",
                          "accessEverything")}).build()))
          .build());
  List<SecurityScheme> authKey = Collections.singletonList(new ApiKey("Authorization", "Authorization", "header"));
// https://blog.csdn.net/Ficar_ver/article/details/124107096
  return new Docket(DocumentationType.OAS_30)
          .pathMapping("/")

          // 定义是否开启swagger，false为关闭，可以通过变量控制，线上关闭
          .enable(enable)

          //配置api文档元信息
          .apiInfo(apiInfo())

          // 选择哪些接口作为swagger的doc发布
          .select()

          //apis() 控制哪些接口暴露给swagger，
          // RequestHandlerSelectors.any() 所有都暴露
          // RequestHandlerSelectors.basePackage("net.xdclass.*") 指定包位置
          // withMethodAnnotation(ApiOperation.class)标记有这个注解 ApiOperation
          .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

          .paths(PathSelectors.any())

          .build().securitySchemes(authKey).securityContexts(securityContexts);
 }

 private ApiInfo apiInfo() {
  return new ApiInfoBuilder()
          .title(applicationName)
          .description(applicationDescription)
          .contact(new Contact("鉴权中心平台接口文档", "www.yifeng.com", "123@qq.com"))
          .version(applicationVersion)
          .build();
 }

}