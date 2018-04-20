package com.oneplatform.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jeesuite.springboot.starter.cache.EnableJeesuiteCache;
import com.jeesuite.springboot.starter.kafka.EnableJeesuiteKafkaConsumer;
import com.jeesuite.springboot.starter.kafka.EnableJeesuiteKafkaProducer;
import com.jeesuite.springboot.starter.mybatis.EnableJeesuiteMybatis;
import com.oneplatform.base.BaseApplicationStarter;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringCloudApplication
@EnableTransactionManagement
@EnableJeesuiteMybatis
@EnableJeesuiteCache
@EnableJeesuiteKafkaProducer
@EnableJeesuiteKafkaConsumer
@MapperScan(basePackages = "com.oneplatform.system.dao.mapper")
@ComponentScan(value = {"com.oneplatform"})
@ServletComponentScan({ "com.oneplatform.base.servlet" })
public class ApplicationStarter extends BaseApplicationStarter{

	public static void main(String[] args) {
		long starTime = before();
		new SpringApplicationBuilder(ApplicationStarter.class).web(true).run(args);
		after(starTime);
	}
	
}
