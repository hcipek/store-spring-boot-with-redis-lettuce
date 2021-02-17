package com.ebebek.reactiveredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@SpringBootApplication
public class ReactiveRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveRedisApplication.class, args);
	}

	//For port configuration
//	@Bean
//	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//		return new LettuceConnectionFactory("localhost", 6379);
//	}

}
