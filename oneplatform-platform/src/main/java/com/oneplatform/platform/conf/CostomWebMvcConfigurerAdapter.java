package com.oneplatform.platform.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oneplatform.platform.interceptor.PlatformGlobalInterceptor;

@Configuration
public class CostomWebMvcConfigurerAdapter implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PlatformGlobalInterceptor()).addPathPatterns("/**");
	}


}
