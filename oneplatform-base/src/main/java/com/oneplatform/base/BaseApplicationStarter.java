package com.oneplatform.base;

import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.common.util.NodeNameHolder;
import com.jeesuite.spring.ApplicationStartedListener;
import com.jeesuite.spring.InstanceFactory;
import com.jeesuite.springweb.client.SimpleRestTemplateBuilder;

public class BaseApplicationStarter {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new SimpleRestTemplateBuilder().build();
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
