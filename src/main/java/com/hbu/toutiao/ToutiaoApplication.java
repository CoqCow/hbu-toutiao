package com.hbu.toutiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class ToutiaoApplication {

/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ToutiaoApplication.class);
	}*/
	public static void main(String[] args) {
		SpringApplication.run(ToutiaoApplication.class, args);
	}
}
