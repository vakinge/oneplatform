package com.oneplatform.core.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.jeesuite.security.SecurityDelegatingFilter;
import com.jeesuite.zuul.filter.ZuulGlobalErrorFilter;  
  
@Configuration  
public class FilterRegConfig {  
	
	@Bean
	@ConditionalOnProperty(name = "zuul.SendErrorFilter.post.disable" ,havingValue = "true")
	public ZuulGlobalErrorFilter zuulGlobalErrorFilter() {
		return new ZuulGlobalErrorFilter();
	}
    
	
	@Bean
	public FilterRegistrationBean<SecurityDelegatingFilter> someFilterRegistration() {
	    FilterRegistrationBean<SecurityDelegatingFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(new SecurityDelegatingFilter());
	    registration.addUrlPatterns("/*");
	    registration.setName("authFilter");
	    registration.setOrder(0);
	    return registration;
	} 
    
	@Bean
	@ConditionalOnProperty(value = "global.cors.enabled", havingValue = "true",matchIfMissing = false)
	public CorsFilter corsFilter() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		configuration.applyPermitDefaultValues();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		CorsFilter filter = new CorsFilter(source);
		return filter;
	}

	
}  
