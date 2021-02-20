package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.Store;
import com.ebebek.reactiveredis.model.StoreResponse;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@RestController
public class StoreController implements Serializable {

    @Autowired
    private RestTemplate restTemplate;

    // inject the actual template
    @Autowired
    private RedisTemplate<String, Store> redisTemplate;
    // string based redis template
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // inject the template as ListOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, Store> listOps;


    public void addLink(String userId, Store store) {
        listOps.leftPush(userId, store);
//        template.opsForList().leftPush(userId, store);
    }

    private RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();

    @GetMapping("/getAll")
    public List<Store> getAll() {
        StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
        return null;
    }
}