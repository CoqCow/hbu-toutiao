package com.hbu.toutiao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class ToutiaoApplication {

/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ToutiaoApplication.class);
	}*/

	@Component
	public static class Config {

		@Value("${jwt.secret}")
		public String jwtSecret;
	}
	public static void main(String[] args) {
		SpringApplication.run(ToutiaoApplication.class, args);
	}
}
