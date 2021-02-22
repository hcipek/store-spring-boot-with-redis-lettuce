package com.ebebek.reactiveredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ReactiveRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveRedisApplication.class, args);
//		new AnnotationConfigApplicationContext(PubSubConfig.class);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
