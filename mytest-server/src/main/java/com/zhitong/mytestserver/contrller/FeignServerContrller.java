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

    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    public List<User> getUsers(){
        List<User> userList  = Lists.newArrayList();
        User user1 = new User();
        user1.setId(1);
        user1.setName("张三");
        user1.setAge(13);
        user1.setSalary("1556546644");
        user1.setPhone("48676441616");
        userList.add(user1);
        User user2 = new User();
        user2.setId(2);
        user2.setName("李四");
        user2.setAge(25);
        user2.setSalary("416464");
        user2.setPhone("468746843646");
        userList.add(user2);
        User user3 = new User();
        user3.setId(3);
        user3.setName("王二麻");
        user3.setAge(45);
        user3.setSalary("154485646");
        user3.setPhone("12654946");
        userList.add(user3);

//        redisTemplate.opsForValue().set("userInfo",JSON.toJSONString(userList), 60*1 , TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("su",JSON.toJSONString(userList), 60*1 , TimeUnit.SECONDS);
        System.out.println("==============");
        Object userInfo = redisTemplate.opsForValue().get("su");
        System.out.println(userInfo);


        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(1);
            user.setName("fgwaeg===="+i);
            user.setId(i);
            user.setPhone("15644646");
            intervalTimerService.getIntervalTimer(user);
        }
        return userList;
    }

}
