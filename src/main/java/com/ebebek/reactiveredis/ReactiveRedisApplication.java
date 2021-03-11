package com.ebebek.reactiveredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
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
