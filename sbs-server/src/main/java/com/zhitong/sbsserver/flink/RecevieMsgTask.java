package com.zhitong.sbsserver.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class RecevieMsgTask {

    @PostConstruct
    public void getMsgByFlink() throws Exception{
        try {
            final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());;
            env.setParallelism(1);

            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", "172.172.1.103:9092");
//        properties.setProperty("zookeeper.connect","172.172.1.103:2181");
            properties.setProperty("group.id", "test");
            properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("mqtt_location_data", new SimpleStringSchema(), properties);
            Map<KafkaTopicPartition, Long> specificStartOffsets = new HashMap<>();
            specificStartOffsets.put(new KafkaTopicPartition("mqtt_location_data", 0), 181L);
            consumer.setStartFromSpecificOffsets(specificStartOffsets);

            DataStream<String> dataStream = env.addSource(consumer);
            dataStream.map(new MapFunction<String, String>() {
                @Override
                public String map(String s) throws Exception {
                    System.out.println(s+"------->");
                    return s;
                }
            });
            env.execute("GetMsgTask");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
