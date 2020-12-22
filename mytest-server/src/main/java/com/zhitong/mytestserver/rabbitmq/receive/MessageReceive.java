package com.zhitong.mytestserver.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.rabbitmq.receive
 * @Description:
 * @date Date : 2020年11月11日 14:47
 */
@Component
public class MessageReceive {

    @RabbitListener(queues = "hello")
    public void receiveMessage1(String string){
        System.out.println("direct接收信息:"+string);
    }

    @RabbitListener(queues = "goods.delete")
    public void receiveMessage2(String string){
        System.out.println("goods.delete队列接收信息:"+string);
    }

    @RabbitListener(queues = "goods.add")
    public void receiveMessage3(String string){
        System.out.println("goods.add队列接收信息:"+string);
    }

    @RabbitListener(queues = "fanout.A")
    public void receiveMessage4(String string){
        System.out.println("fanout.A队列接收信息:"+string);
    }

    @RabbitListener(queues = "fanout.B")
    public void receiveMessage5(String string){
        System.out.println("fanout.B队列接收信息:"+string);
    }
}
