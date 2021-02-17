package com.ebebek.reactiveredis.hello;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class CoffeeController {
    private final ReactiveRedisOperations<String, Coffee> coffeeOps;
    private final ReactiveValueOperations<String, Coffee> coffeeValueOps;
    private RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();

    CoffeeController(ReactiveRedisOperations<String, Coffee> coffeeOps) {
        this.coffeeOps = coffeeOps;
        this.coffeeValueOps = this.coffeeOps.opsForValue();
    }

    @GetMapping("/coffees")
    public Flux<Coffee> all() {
        return coffeeOps.keys("*")
                .flatMap(coffeeOps.opsForValue()::get);
    }

    @PostMapping("/renameKey")
    public String renameKey(@RequestBody KeyRequest req) {
//        coffeeOps.
        return "OK";
    }

    @PostMapping("/setrandom")
    public Mono<Boolean> setRandom() {
        Coffee coffee = new Coffee();
        System.out.println("asdsadsad");
        coffee.setId(UUID.randomUUID().toString());
        coffee.setName(generator.generate(20));
        Mono<Boolean> bool = coffeeOps.opsForValue().set(coffee.getId(), coffee);
        return bool;
    }

    @GetMapping("/get")
    public Mono<Coffee> getById(@RequestParam("key") String key) {
        return coffeeOps.opsForValue().get(key);
    }
}