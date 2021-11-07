package com.oneplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jeesuite.springboot.starter.cache.EnableJeesuiteCache;
import com.jeesuite.springboot.starter.mybatis.EnableJeesuiteMybatis;
import com.jeesuite.springweb.base.BaseApplicationStarter;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringCloudApplication
@EnableJeesuiteCache
@EnableJeesuiteMybatis
@EnableTransactionManagement
@MapperScan(basePackages = {"com.oneplatform.modules.system.dao.mapper","com.oneplatform.modules.organization.dao.mapper"})
@ComponentScan(value = {"com.oneplatform","com.jeesuite"})
@ServletComponentScan({ "com.oneplatform" })
public class ApplicationStarter extends BaseApplicationStarter{

	public static void main(String[] args) {
		long starTime = before();
		new SpringApplicationBuilder(ApplicationStarter.class).web(WebApplicationType.SERVLET).run(args);
		after(starTime);
	}
	
	
	
}
