package com.oneplatform.common.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.oneplatform.base.interceptor.GlobalDefaultInterceptor;

@Configuration
public class CostomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new GlobalDefaultInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
	}


}
