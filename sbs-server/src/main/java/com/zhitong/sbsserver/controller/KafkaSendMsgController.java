package com.zhitong.sbsserver.controller;

import com.zhitong.sbsserver.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka/send")
public class KafkaSendMsgController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping(value = "/msg",method = RequestMethod.GET)
    public void sendMsg(@RequestParam(value = "msg") String msg){
        kafkaProducer.send(msg);
    }
}
