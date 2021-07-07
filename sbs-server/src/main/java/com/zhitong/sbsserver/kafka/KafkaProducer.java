package com.zhitong.sbsserver.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;


    /**
     * 发送kafka消息
     *
     * @param jsonString
     */
    public void send(String jsonString) {
        ListenableFuture future = kafkaTemplate.send(topic, jsonString);
        future.addCallback(o -> log.info("kafka消息发送成功：" + jsonString), throwable -> log.error("kafka消息发送失败：" + jsonString));
    }

}
