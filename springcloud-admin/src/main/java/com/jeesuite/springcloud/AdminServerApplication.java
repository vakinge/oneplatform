package com.jeesuite.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import de.codecentric.boot.admin.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AdminServerApplication {

	public static void main(String[] args) {
		long starTime = System.currentTimeMillis();
		new SpringApplicationBuilder(AdminServerApplication.class).web(true).run(args);
		long endTime = System.currentTimeMillis();
		long time = endTime - starTime;
		System.out.println("\nStart Time: " + time / 1000 + " s");
		System.out.println("...............................................................");
		System.out.println(".........AdminServerApplication starts successfully...........");
		System.out.println("...............................................................");

	}
}
