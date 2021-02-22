package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.store.Store;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
public class ExampleController implements Serializable {

    private RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();

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
}