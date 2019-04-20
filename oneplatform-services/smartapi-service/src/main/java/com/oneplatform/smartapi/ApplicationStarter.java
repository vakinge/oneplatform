package com.oneplatform.smartapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jeesuite.springboot.starter.cache.EnableJeesuiteCache;
import com.jeesuite.springboot.starter.mybatis.EnableJeesuiteMybatis;
import com.oneplatform.base.BaseApplicationStarter;


@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableJeesuiteMybatis
@EnableJeesuiteCache
@MapperScan(basePackages = "com.jeesuite.smartapi.dao.mapper")
@ComponentScan(value = {"com.oneplatform","com.jeesuite.smartapi"})
@ServletComponentScan({ "com.oneplatform.base.servlet","com.jeesuite.smartapi.core" })
public class ApplicationStarter extends BaseApplicationStarter{

	public static void main(String[] args) {
		long starTime = before();
		new SpringApplicationBuilder(ApplicationStarter.class).web(WebApplicationType.SERVLET).run(args);
		after(starTime);
	}

}
