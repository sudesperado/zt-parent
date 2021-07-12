package com.zhitong.sbsserver.task;

import com.zhitong.sbsserver.kafka.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.event.ConsumerStoppedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgRecieveTask extends KafkaConsumer {

    @Override
    protected void onDealMessage(String message) {
        try {
            Thread.sleep(5000);
            log.info("当前线程："+Thread.currentThread().getName()+"\n 消费信息："+ message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onApplicationEvent(ConsumerStoppedEvent consumerStoppedEvent) {

    }
}
