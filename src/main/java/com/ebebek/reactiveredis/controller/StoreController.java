package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.Store;
import com.ebebek.reactiveredis.model.StoreResponse;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.UUID;

@RestController
public class StoreController implements Serializable {

    @Autowired
    private RestTemplate restTemplate;

    private final ReactiveRedisOperations<String, Store> storeOps;
    private final ReactiveValueOperations<String, Store> storeValueOps;
    private RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();

    StoreController(ReactiveRedisOperations<String, Store> storeOps) {
        this.storeOps = storeOps;
        this.storeValueOps = this.storeOps.opsForValue();
    }

    @GetMapping("/getAllKeys")
    public Flux<Store> getAllKeys() {
        return storeOps.keys("*")
                .flatMap(storeOps.opsForValue()::get);
    }

    @GetMapping("/get")
    public Mono<Store> getById(@RequestParam("key") String key) {
        return storeOps.opsForValue().get(key);
    }

    @Cacheable("'store'")
    @GetMapping("/getAll")
    public Flux<Store> getAll() {
        StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
        response.getResults().forEach(e -> storeValueOps.set(e.getId().toString(), e));
        Flux<Store> storeFlux = Flux.fromIterable(response.getResults());
        return storeFlux;
    }
}