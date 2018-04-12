package com.preEmynence.multiTenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.preEmynence")
public class MultiTenantDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantDemoApplication.class, args);
	}


}