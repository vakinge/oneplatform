package com.oneplatform.platform.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.oneplatform.platform.interceptor.PlatformGlobalInterceptor;

@Configuration
public class CostomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PlatformGlobalInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
	}


}
