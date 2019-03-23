package com.oneplatform.user;

import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jeesuite.springboot.starter.cache.EnableJeesuiteCache;
import com.jeesuite.springboot.starter.kafka.EnableJeesuiteKafkaConsumer;
import com.jeesuite.springboot.starter.kafka.EnableJeesuiteKafkaProducer;
import com.jeesuite.springboot.starter.mybatis.EnableJeesuiteMybatis;
import com.jeesuite.springboot.starter.scheduler.EnableJeesuiteSchedule;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.BaseApplicationStarter;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@MapperScan(basePackages = "com.oneplatform.user.dao.mapper")
@EnableJeesuiteCache
@EnableJeesuiteMybatis
@EnableJeesuiteSchedule
@EnableJeesuiteKafkaConsumer
@EnableJeesuiteKafkaProducer
@ComponentScan(value = {"com.oneplatform","com.oneplatform.user"})
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients
@EnableCircuitBreaker
@ServletComponentScan({ "com.oneplatform.base.servlet" })
public class ApplicationStarter extends BaseApplicationStarter {


	public static void main(String[] args) {
		long starTime = before();
		new SpringApplicationBuilder(ApplicationStarter.class).web(WebApplicationType.SERVLET).run(args);
		after(starTime);
	}

	
	@Bean
	public Feign.Builder feignBuilder() {
		return Feign.builder().requestInterceptor(new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				Map<String, String> customHeaders = WebUtils.getCustomHeaders();
				customHeaders.forEach((k,v)->{					
					requestTemplate.header(k, v);
				});  
			}
		});
	}
}
