package com.zhitong.mytestserver.rabbitmq.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.rabbitmq.send
 * @Description:
 * @date Date : 2020年11月11日 14:09
 */
@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void send(){
        String context = "hello " + format.format(new Date());
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    public void sendTopic(){
        String context = "发送一个topic消息 " + format.format(new Date());
        System.out.println("Sender : " + context);
        /*param1:交换机名称;param2:路由键;param3:发送消息内容*/
        this.rabbitTemplate.convertAndSend("exchange","goods.update", context);
    }

    public void sendFanout(){
        String context = "发送一个fanout消息 " + format.format(new Date());
        System.out.println("Sender : " + context);
        /*param1:交换机名称;param2:路由键;param3:发送消息内容*/
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
    }
}
