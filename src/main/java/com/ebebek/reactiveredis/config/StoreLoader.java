package com.ebebek.reactiveredis.config;

import com.ebebek.reactiveredis.model.Store;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class StoreLoader {
    private final RedisConnectionFactory factory;
    private final RedisOperations<String, Store> storeOps;

    public StoreLoader(RedisConnectionFactory factory, RedisOperations<String, Store> storeOps) {
        this.factory = factory;
        this.storeOps = storeOps;
    }

    @PostConstruct
    public void loadData() {
        factory.getConnection().serverCommands().flushAll();
//        .thenMany(
//            Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
//                .map(name -> new Coffee(UUID.randomUUID().toString(), name))
//                .flatMap(coffee -> coffeeOps.opsForValue().set(coffee.getId(), coffee))
//        )
//        .thenMany(coffeeOps.keys("*")
//        .flatMap(coffeeOps.opsForValue()::get))
//        .subscribe(System.out::println);
    }

    // A StatefulRedisConnection is what it sounds like; a thread-safe connection to a Redis server that will
    // maintain its connection to the server and reconnect if needed. Once we have a connection,
    // we can use it to execute Redis commands either synchronously or asynchronously.
    //
    // RedisClient uses substantial system resources, as it holds Netty resources for communicating with the Redis
    // server. Applications that require multiple connections should use a single RedisClient.
    private void createClientConnection() {
        RedisClient client = RedisClient.create("");
        StatefulRedisConnection<String, String> statefulRedisConnection = client.connect();
    }

    private RedisURI redisURIBuilder() {
        RedisURI uri = RedisURI.Builder
                .redis("localhost", 6379)
                .withAuthentication("username","password")
                .withDatabase(1)
                .build();
        uri = new RedisURI("", 6379, Duration.ofSeconds(60));
        return uri;

    }


}