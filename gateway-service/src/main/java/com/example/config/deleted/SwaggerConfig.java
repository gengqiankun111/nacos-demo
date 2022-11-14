package com.example.config.deleted;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

/**
 * swagger配制
 *
 * @author teler
 * @date 2020-12-30
 */
//@Configuration
	@Deprecated
public class SwaggerConfig {

	@Value("${swagger.enable}")
	private Boolean enable = true;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
					   //资源
				       .globalResponses(HttpMethod.GET, new ArrayList<>())
				       .globalResponses(HttpMethod.PUT, new ArrayList<>())
				       .globalResponses(HttpMethod.POST, new ArrayList<>())
				       .globalResponses(HttpMethod.DELETE, new ArrayList<>())
					   //是否启动
				       .enable(enable)
					   //头部信息
				       .apiInfo(apiInfo())
				       .select()
				       /**
				        * RequestHandlerSelectors,配置要扫描接口的方式
				        * basePackage指定要扫描的包
				        * any()扫描所有，项目中的所有接口都会被扫描到
				        * none()不扫描
				        * withClassAnnotation()扫描类上的注解
				        * withMethodAnnotation()扫描方法上的注解
				        */
				       .apis(RequestHandlerSelectors.any())
					   //过滤某个路径
				       .paths(PathSelectors.any())
				       .build()
					   //协议
				       .protocols(newHashSet("https", "http"))
				       //.securitySchemes(securitySchemes())
				       //.securityContexts(securityContexts())
				//.globalRequestParameters(getGlobalRequestParameters())
				;
	}

	//生成全局通用参数
	private List<RequestParameter> getGlobalRequestParameters() {
		List<RequestParameter> parameters = new ArrayList<>();
		parameters.add(new RequestParameterBuilder()
				.name("authorization")
				.description("token")
				.required(true)
				.in(ParameterType.HEADER)
				.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
				.required(true)
				.build());
		parameters.add(new RequestParameterBuilder()
				.name("udid")
				.description("设备的唯一id")
				.required(true)
				.in(ParameterType.QUERY)
				.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
				.required(false)
				.build());
		parameters.add(new RequestParameterBuilder()
				.name("version")
				.description("客户端的版本号")
				.required(true)
				.in(ParameterType.QUERY)
				.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
				.required(false)
				.build());
		return parameters;
	}


	/**
	 * API 页面上半部分展示信息
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				       .title("接口文档")
				       .description("@author Teler")
				       .contact(new Contact("Teler", null, "teler96@qq.com"))
				       .version("1.0")
				       .build();
	}

	/**
	 * 设置接口单独的授权信息
	 */
	private List<SecurityScheme> securitySchemes() {
		SecurityScheme ss = HttpAuthenticationScheme.JWT_BEARER_BUILDER
//                        显示用
				.name("JWT")
				.build();
		//return Collections.singletonList(ss);
		return Collections.singletonList(new ApiKey("Authorization", "authorization", "header"));
		//return Collections.singletonList(new ApiKey("BASE_TOKEN", "token", "header"));
	}

	/**
	 * 授权信息全局应用
	 */
	private List<SecurityContext> securityContexts() {
		return Collections.singletonList(
				SecurityContext.builder()
						.securityReferences(
								Collections.singletonList(new SecurityReference("BEAR_TOKEN", //BASE_TOKEN
										new AuthorizationScope[]{new AuthorizationScope("global", "")}
								)))
						//.forPaths(PathSelectors.any())
						.build()
		);
//		return Collections.singletonList(SecurityContext.builder()
//				.securityReferences(Collections.singletonList(SecurityReference.builder()
//						.scopes(new AuthorizationScope[0])
//						.reference("JWT")
//						.build()))
//				// 声明作用域
//				.operationSelector(o -> o.requestMappingPattern().matches("/.*"))
//				.build());
	}

	@SafeVarargs
	private final <T> Set<T> newHashSet(T... ts) {
		if (ts.length > 0) {
			return new LinkedHashSet<>(Arrays.asList(ts));
		}
		return null;
	}
}