package com.zhitong.sbsserver.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/flink")
public class FlinkKafkaMsgConsumer {

    @RequestMapping(value = "/sendMsgbyFlink",method = RequestMethod.GET)
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

    @RequestMapping(value = "/getMsgbyFlink",method = RequestMethod.GET)
    public void getMsgByFlink() throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "172.172.1.103:9092");
        properties.setProperty("group.id", "test");
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("mqtt_location_data", new SimpleStringSchema(), properties);
        DataStream<String> dataStream = env.addSource(consumer);
       dataStream.print();
    }
}
