package com.zhitong.mytestserver.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.rabbitmq.config
 * @Description:
 * @date Date : 2020年11月11日 14:06
 */
@Configuration
public class RabbitConfig {
    //1.direct模式
    @Bean
    public Queue queue(){
        return new Queue("hello");
    }

    //2.topic模式
    @Bean(name = "delete")
    public Queue topicQueue1(){
        return new Queue("goods.delete");
    }

    @Bean(name = "add")
    public Queue topicQueue2(){
        return new Queue("goods.add");
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange("exchange");
    }

    @Bean
    public Binding bindExchange1(@Qualifier("delete") Queue queue, TopicExchange exchange){
       return BindingBuilder.bind(queue).to(exchange).with("goods.add");
    }

    @Bean
    public Binding bindExchange2(@Qualifier("add") Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("goods.#");
    }

    //3.fanout模式
    @Bean(name="Amessage")
    public Queue AMessage() {
        return new Queue("fanout.A");
    }


    @Bean(name="Bmessage")
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");//配置广播路由器
    }

    @Bean
    Binding bindingExchangeA(@Qualifier("Amessage") Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(@Qualifier("Bmessage") Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

}
