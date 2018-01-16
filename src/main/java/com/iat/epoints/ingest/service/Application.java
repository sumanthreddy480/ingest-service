package com.iat.epoints.ingest.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.iat.epoints.ingest.service")
public class Application extends SpringBootServletInitializer {

	private static final Class<Application> APPLICATION_CLASS = Application.class;

	public static void main(String[] args) {
		SpringApplication.run(APPLICATION_CLASS, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder APPLICATION_CLASS) {
		return super.configure(APPLICATION_CLASS);
	}
}
