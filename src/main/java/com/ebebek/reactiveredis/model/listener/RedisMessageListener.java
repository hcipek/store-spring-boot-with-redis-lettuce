package com.ebebek.reactiveredis.model.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
@Slf4j
public class RedisMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("Message received : {}", message.toString());
    }
}
