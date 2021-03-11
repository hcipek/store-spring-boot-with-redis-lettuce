package com.ebebek.reactiveredis;

import com.ebebek.reactiveredis.config.PubSubConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RedisPubSubStarter {
    public static void main(String[] args) {
            new AnnotationConfigApplicationContext(PubSubConfig.class);
    }
}