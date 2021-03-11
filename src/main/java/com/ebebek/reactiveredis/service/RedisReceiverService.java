package com.ebebek.reactiveredis.service;

import com.ebebek.reactiveredis.model.queue.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class RedisReceiverService implements MessageListener {

    private ListOperations<String, String> listOperations;

    private static final String REDIS_QUEUE_KEY = "RMS";

    public RedisReceiverService(RedisTemplate redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public void onMessage(final Message message, final byte[] bytes) {
        log.info("RedisReceiverService Received Message : {}", message.toString());
        rightPushMessage(message.toString());
    }

    private void rightPushMessage(String message) {
        listOperations.rightPush(REDIS_QUEUE_KEY, message);
    }

    public String getNext() {
        return listOperations.leftPop(REDIS_QUEUE_KEY);
    }

    public List<String> getWithSize(int size) {
        List<String> items = new ArrayList<>();
        for(int i=0; i<size; i++) {
            String item = listOperations.leftPop(REDIS_QUEUE_KEY);
            if(item == null)
                break;
            items.add(item);
        }
        return items;
    }

    public Long getSize() {
        return listOperations.size(REDIS_QUEUE_KEY);
    }

}