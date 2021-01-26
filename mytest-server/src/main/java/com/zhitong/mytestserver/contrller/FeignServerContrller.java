package com.zhitong.mytestserver.contrller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhitong.mytestserver.model.User;
import com.zhitong.mytestserver.service.IntervalTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.contrller
 * @Description:
 * @date Date : 2020年12月04日 17:11
 */
@RestController
@RequestMapping("/hello/api")
public class FeignServerContrller {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IntervalTimerService intervalTimerService;


    @RequestMapping(value = "/getStr",method = RequestMethod.GET)
    public void getString(){
        System.out.println("输出信息:哈哈哈");
    }

}
