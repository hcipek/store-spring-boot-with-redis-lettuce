package com.ebebek.reactiveredis.service;

import com.ebebek.reactiveredis.model.store.Store;
import com.ebebek.reactiveredis.model.store.StoreRequest;
import com.ebebek.reactiveredis.model.store.StoreResponse;
import com.ebebek.reactiveredis.model.ui.store.UIStoreRequest;
import com.ebebek.reactiveredis.util.ResponseCodesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.Cacheable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class StoreService extends BaseService {

    @Autowired
    private RestTemplate restTemplate;

    private ValueOperations<String, Store> valueOperations;
    private HashOperations<String, String, Store> hashOperations;
    private ListOperations<String, Store> listOperations;

    private static final String STORE_KEY = "Stores";

    public StoreService(RedisTemplate redisTemplate) {
        redisTemplate.setHashValueSerializer(serializerSetup());
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        valueOperations = redisTemplate.opsForValue();
    }

    public StoreResponse createStore(UIStoreRequest request) {
        StoreResponse response = restTemplate.postForObject("http://localhost:9081/api/store/create", request, StoreResponse.class);
        return onSaveHash(new StoreRequest(response.getResults()));
    }

    /**
     * Set Operations For Redis
     * getAll from another project and sets it to Certain key as HashMap
     * onSave when a data inserted another project sends data to this method and sets the value
     * onUpdate when a data updated another project sends data to this method and sets the value
     */
    @Cacheable(STORE_KEY + "_Values")
    public List<Store> getAll() {
        StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
        multiSetValues(response.getResults());
        return response.getResults();
    }

    public StoreResponse onSave(StoreRequest request) {
        return multiSetValues(request.getRequestList());
    }

    public StoreResponse onUpdate(StoreRequest request) {
        return multiSetValues(request.getRequestList());

    }

    private StoreResponse multiSetValues(List<Store> list) {
        valueOperations.multiSet(convertListToMap(list));
        return new StoreResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    /**
     * Hash Operations For Redis
     * getAll from another project and puts it to Certain key as HashMap
     * onSave when a data inserted another project sends data to this method and puts the value
     * onUpdate when a data updated another project sends data to this method and puts the value
     */

    //Puts all data to a Map with keys and puts that map into another key
    public Collection<Store> getAllHash() {
        if(hashOperations.size("Stores") == 0) {
            StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
            putAllValues(response.getResults());
        }
        return hashOperations.entries(STORE_KEY).values();
    }

    public StoreResponse onSaveHash(StoreRequest request) {
        return putAllValues(request.getRequestList());
    }

    public StoreResponse onUpdateHash(StoreRequest request) {
        return putAllValues(request.getRequestList());
    }

    public Store getByHashKey(Long id) {
        return hashOperations.get(STORE_KEY, id.toString());
    }

    private StoreResponse putAllValues(List<Store> list) {
        hashOperations.putAll(STORE_KEY, convertListToMap(list));
        return new StoreResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    /**
     * List Operations For Redis
     *
     *
     */

    public List<Store> getAllList() {
        Long size = listOperations.size(STORE_KEY);
        if(size.equals(Long.valueOf(0))) {
            StoreResponse response = restTemplate.getForObject("http://localhost:9081/api/store/getall", StoreResponse.class);
            listOperations.rightPushAll(STORE_KEY, response.getResults());
        }
        return listOperations.range(STORE_KEY, 0, size.intValue());
    }

    public List<Store> getListByPaging(int pageIndex, int pageSize) {
        return listOperations.range(STORE_KEY, pageIndex*pageSize - pageSize, pageSize*pageIndex - 1);
    }

    private StoreResponse rightPushAllValues(List<Store> list) {
        listOperations.rightPushAll(STORE_KEY, list);
        return new StoreResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    private Map<String, Store> convertListToMap(List<Store> storeList) {
        return storeList.stream().collect(Collectors.toMap(e -> e.getId().toString(), e -> e));
    }
}
