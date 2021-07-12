package com.zhitong.sbsserver.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ConsumerStoppedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public abstract class KafkaConsumer implements ApplicationListener<ConsumerStoppedEvent> {

    private ThreadPoolExecutor consumeExecutor;

    @PostConstruct
    public void init() {
        this.consumeExecutor = new ThreadPoolExecutor(
                5,
                10,
                // 此处最大最小不一样没啥大的意义,因为消息队列需要达到 Integer.MAX_VALUE 才有点作用，
                // 矛盾来了，我每次批量拉下来不可能设置Integer.MAX_VALUE这么多，
                // 个人觉得每次批量下拉的原则 觉得消费可控就行，
                // 不然，如果出现异常情况下，整个服务示例突然挂了，拉下来太多，这些消息会被重复消费一次。
                1000 * 60,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @KafkaListener(topics = "mqtt_location_data")
    public void listen(ConsumerRecord<?, ?> record) {
        submitConsumeTask(record.value().toString());
    }

    private void submitConsumeTask(String message) {
        consumeExecutor.submit(() -> {
            try {
                onDealMessage(message);
            } catch (Exception ex) {
                log.error("on DealMessage exception:", ex);
            }
        });
    }

    /**
     * 子类实现该抽象方法处理具体消息的业务逻辑
     * @param message kafka的消息
     */
    protected abstract void onDealMessage(String message);

}
