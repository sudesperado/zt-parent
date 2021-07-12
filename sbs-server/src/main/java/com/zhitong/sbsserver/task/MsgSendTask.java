package com.zhitong.sbsserver.task;


import com.zhitong.sbsserver.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MsgSendTask {

    @Autowired
    private KafkaProducer kafkaProducer;

    private Integer i = 0;

    @PostConstruct
    private void initialize() {
        load();
    }

    //每秒发送一条消息
    @Scheduled(cron="*/1 * * * * ?")
    private void load() {
        String msg = "消息发送---"+i;
        i++;
        System.out.println(msg);
        kafkaProducer.send(msg);
    }
}
