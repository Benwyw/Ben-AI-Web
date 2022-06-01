package com.benwyw.config;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();
	    tokenBuilder
	            .name("Authorization")
	            .description("access_token")
	            .required(false)
	            .in("header")
	            .accepts(Collections.singleton(MediaType.APPLICATION_JSON))
	            .query(q -> q.defaultValue("Bearer"))
	            .build();
		List<RequestParameter> aParameters = new ArrayList<>();
		aParameters.add(tokenBuilder.build());
		return new Docket(DocumentationType.OAS_30).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**")).build().pathMapping("").globalRequestParameters(aParameters)
				.ignoredParameterTypes(Principal.class);
	}
}