package com.zhitong.sbsserver.flink;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ProducerMsgTask {

//    @Scheduled(cron = "*/15 * * * * ?")
    public void sendMsgByFlink(){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "172.172.1.103:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        ProducerRecord record = new ProducerRecord<String, String>("mqtt_location_data", null, null, "hello world");
        kafkaProducer.send(record);
        System.out.println("发送消息内容：hello world");
        kafkaProducer.flush();
    }
}
