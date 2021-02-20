package com.ebebek.reactiveredis.config;

import com.ebebek.reactiveredis.model.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class StoreConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("server", 6379));
    }

//    WriteToMasterReadFromReplicaConfiguration
    /*
    @Bean
    public LettuceConnectionFactory redisConnectionFactory2() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(SLAVE_PREFERRED)
                .build();

        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("server", 6379);

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }
    */
//    RedisConfiguration.SentinelConfiguration
    /*
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory3() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("127.0.0.1", 26379)
                .sentinel("127.0.0.1", 26380);
        return new LettuceConnectionFactory(sentinelConfig);
    }
    */
}