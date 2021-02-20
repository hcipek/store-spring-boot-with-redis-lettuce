package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.Store;
import com.ebebek.reactiveredis.model.StoreResponse;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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

//    public void useCallback() {
//
//        stringRedisTemplate.execute(new RedisCallback<Object>() {
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                Long size = connection.dbSize();
//                // Can cast to StringRedisConnection if using a StringRedisTemplate
//                ((StringRedisConnection)connection).set("key", "value");
//            }
//        });
//    }
}