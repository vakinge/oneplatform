package com.oneplatform.core.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeesuite.zuul.router.CustomDiscoveryClientRouteLocator;  
  
@Configuration  
@ConditionalOnProperty(name = "eureka.client.enabled",havingValue = "true",matchIfMissing = true)
public class ZuulConfig {  
	
    @Bean  
    public CustomDiscoveryClientRouteLocator discoveryClientRouteLocator(DiscoveryClient discovery,
			ZuulProperties properties, ServiceRouteMapper serviceRouteMapper,
			ServiceInstance localServiceInstance,ServerProperties server){
    	String servletPath = server.getServlet().getContextPath();
    	return new CustomDiscoveryClientRouteLocator(servletPath, discovery, properties, serviceRouteMapper, localServiceInstance);
    }
  
}  
