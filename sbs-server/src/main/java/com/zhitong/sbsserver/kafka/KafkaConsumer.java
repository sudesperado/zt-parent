package com.zhitong.sbsserver.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "mqtt_location_data")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("消费信息：topic={}, offset={}, message={}", record.topic(), record.offset(), record.value());
    }
}
