package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.cart.Cart;
import com.ebebek.reactiveredis.model.cart.CartRequest;
import com.ebebek.reactiveredis.model.cart.CartResponse;
import com.ebebek.reactiveredis.model.store.Store;
import com.ebebek.reactiveredis.model.store.StoreRequest;
import com.ebebek.reactiveredis.model.store.StoreResponse;
import com.ebebek.reactiveredis.model.ui.store.UIStoreRequest;
import com.ebebek.reactiveredis.util.ResponseCodesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/redis/store")
public class StoreController implements Serializable {

    @Autowired
    private RestTemplate restTemplate;
    // inject the actual template
    @Autowired
    private RedisTemplate<String, Store> redisTemplate;
    // string based redis template
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String STORE_KEY = "Stores";

    @PostMapping("/createStore")
    public StoreResponse createStore(@RequestBody UIStoreRequest request) {
        StoreResponse response = restTemplate.postForObject("http://localhost:9081/api/store/create", request, StoreResponse.class);
        return onSaveHash(new StoreRequest(response.getResults()));
    }

    /**
     * Set Operations For Redis
     * getAll from another project and sets it to Certain key as HashMap
     * onSave when a data inserted another project sends data to this method and sets the value
     * onUpdate when a data updated another project sends data to this method and sets the value
     */

    @GetMapping("/getAll")
    public List<Store> getAll() {
        StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
        multiSetValues(response.getResults());
        return response.getResults();
    }

    @PostMapping("/onSave")
    public StoreResponse onSave(@RequestBody StoreRequest request) {
        return multiSetValues(request.getRequestList());
    }

    @PostMapping("/onUpdate")
    public StoreResponse onUpdate(@RequestBody StoreRequest request) {
        return multiSetValues(request.getRequestList());

    }

    private StoreResponse multiSetValues(List<Store> list) {
        redisTemplate.opsForValue().multiSet(convertListToMap(list));
        return new StoreResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    /**
     * Hash Operations For Redis
     * getAll from another project and puts it to Certain key as HashMap
     * onSave when a data inserted another project sends data to this method and puts the value
     * onUpdate when a data updated another project sends data to this method and puts the value
     */

    //Puts all data to a Map with keys and puts that map into another key
    @GetMapping("/getAllHash")
    public Collection<Object> getAllHash() {
        if(redisTemplate.opsForHash().size("Stores") == 0) {
            StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
            putAllValues(response.getResults());
        }
        return redisTemplate.opsForHash().entries(STORE_KEY).values();
    }

    @PostMapping("/onSaveHash")
    public StoreResponse onSaveHash(@RequestBody StoreRequest request) {
        return putAllValues(request.getRequestList());
    }

    @PostMapping("/onUpdateHash")
    public StoreResponse onUpdateHash(@RequestBody StoreRequest request) {
        return putAllValues(request.getRequestList());
    }

    private StoreResponse putAllValues(List<Store> list) {
        redisTemplate.opsForHash().putAll(STORE_KEY, convertListToMap(list));
        return new StoreResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    /**
     * List Operations For Redis
     *
     *
     */

    @GetMapping("/getAllList")
    public List<Store> getAllList() {
        Long size = redisTemplate.opsForList().size(STORE_KEY);
        if(size.equals(Long.valueOf(0))) {
            StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
            redisTemplate.opsForList().rightPushAll(STORE_KEY, response.getResults());
        }
        return redisTemplate.opsForList().range(STORE_KEY, 0, size.intValue());
    }

    @GetMapping("/getListByPaging")
    public List<Store> getListByPaging(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize) {
        return redisTemplate.opsForList().range(STORE_KEY, pageIndex*pageSize - pageSize, pageSize*pageIndex - 1);
    }

    private StoreResponse rightPushAllValues(List<Store> list) {
        redisTemplate.opsForList().rightPushAll(STORE_KEY, list);
        return new StoreResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    private Map<String, Store> convertListToMap(List<Store> storeList) {
        return storeList.stream().collect(Collectors.toMap(e -> e.getId().toString(), e -> e));
    }

}