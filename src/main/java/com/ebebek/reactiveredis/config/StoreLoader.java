package com.ebebek.reactiveredis.config;

import com.ebebek.reactiveredis.model.Store;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StoreLoader {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Store> storeOps;

    public StoreLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Store> storeOps) {
        this.factory = factory;
        this.storeOps = storeOps;
    }

    @PostConstruct
    public void loadData() {
        factory.getReactiveConnection()
                .serverCommands()
        .flushAll();
//        .thenMany(
//            Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
//                .map(name -> new Coffee(UUID.randomUUID().toString(), name))
//                .flatMap(coffee -> coffeeOps.opsForValue().set(coffee.getId(), coffee))
//        )
//        .thenMany(coffeeOps.keys("*")
//        .flatMap(coffeeOps.opsForValue()::get))
//        .subscribe(System.out::println);
    }
}