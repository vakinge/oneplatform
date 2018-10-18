package com.oneplatform.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.common.util.NodeNameHolder;
import com.jeesuite.spring.ApplicationStartedListener;
import com.jeesuite.spring.InstanceFactory;
import com.jeesuite.springweb.client.CustomResponseErrorHandler;
import com.jeesuite.springweb.client.RestTemplateAutoHeaderInterceptor;

public class BaseApplicationStarter {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(15000);//ms  
        factory.setConnectTimeout(10000);//ms 
        
        RestTemplate restTemplate = new RestTemplate(factory);
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RestTemplateAutoHeaderInterceptor());
        restTemplate.setInterceptors(interceptors);
        //
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		return restTemplate;
	}

	@Bean
	RestTemplate restTemplateNoneLB() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(30000);
		return new RestTemplate(factory);
	}

	protected static long before() {
		System.setProperty("client.nodeId", NodeNameHolder.getNodeId());
		return System.currentTimeMillis();
	}

	protected static void after(long starTime) {
		//
		long endTime = System.currentTimeMillis();
		long time = endTime - starTime;
		System.out.println("\nStart Time: " + time / 1000 + " s");
		System.out.println("...............................................................");
		System.out.println("..................Service starts successfully..................");
		System.out.println("...............................................................");

		Map<String, ApplicationStartedListener> interfaces = InstanceFactory.getInstanceProvider()
				.getInterfaces(ApplicationStartedListener.class);
		if (interfaces != null) {
			for (ApplicationStartedListener listener : interfaces.values()) {
				System.out.println(">>>begin to execute listener:" + listener.getClass().getName());
				listener.onApplicationStarted(InstanceFactory.getContext());
				System.out.println("<<<<finish execute listener:" + listener.getClass().getName());
			}
		}
	}

}
