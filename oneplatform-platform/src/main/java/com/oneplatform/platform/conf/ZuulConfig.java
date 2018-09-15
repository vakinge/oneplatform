package com.oneplatform.platform.conf;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oneplatform.platform.zuul.CustomRouteLocator;  
  
@Configuration  
public class ZuulConfig {  
	
    @Bean  
    public CustomRouteLocator routeLocator( ZuulProperties zuulProperties,ServerProperties server) {  
        CustomRouteLocator routeLocator = new CustomRouteLocator(server.getServlet().getServletPrefix(), zuulProperties);  
        return routeLocator;  
    }  
  
}  
