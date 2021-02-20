package com.ebebek.reactiveredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class ReactiveRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveRedisApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RedisTemplate getRedisTemplate() {
			return new RedisTemplate();
	}
	//For port configuration
//	@Bean
//	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//		return new LettuceConnectionFactory("localhost", 6379);
//	}

}
