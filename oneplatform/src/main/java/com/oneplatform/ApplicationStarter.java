package com.oneplatform;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jeesuite.springboot.starter.BaseApplicationStarter;
import com.jeesuite.zuul.task.SystemMateRefreshTask;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringCloudApplication
@EnableTransactionManagement
@ComponentScan(value = {"com.oneplatform","com.jeesuite.springboot.autoconfigure","com.jeesuite.zuul.autoconfigure"})
public class ApplicationStarter extends BaseApplicationStarter{

	public static void main(String[] args) {
		long starTime = before();
		new SpringApplicationBuilder(ApplicationStarter.class).web(WebApplicationType.SERVLET).run(args);
		after(starTime);
	}
	
	@Bean
	public SystemMateRefreshTask systemMateRefreshTask() {
		return new SystemMateRefreshTask();
	}
	
}
