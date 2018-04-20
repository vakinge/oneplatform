package com.oneplatform.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jeesuite.springboot.starter.cache.EnableJeesuiteCache;
import com.jeesuite.springboot.starter.mybatis.EnableJeesuiteMybatis;
import com.oneplatform.base.BaseApplicationStarter;


@EnableDiscoveryClient
@SpringCloudApplication
@EnableTransactionManagement
@EnableJeesuiteMybatis
@EnableJeesuiteCache
@MapperScan(basePackages = "com.oneplatform.common.dao.mapper")
@ComponentScan(value = {"com.oneplatform"})
public class ApplicationStarter extends BaseApplicationStarter{

	public static void main(String[] args) {
		long starTime = before();
		new SpringApplicationBuilder(ApplicationStarter.class).web(true).run(args);
		after(starTime);
	}
}
