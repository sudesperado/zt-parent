package com.zhitong.mytestserver.transaction.controller;


import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.transaction.entity.User;
import com.zhitong.mytestserver.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.transaction.controller
 * @Description:
 * @date Date : 2020年11月27日 14:28
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public Result InsertUser(){
        try {
            User user = new User();
            user.setId(2L);
            user.setName("yao");
            user.setAge(25);
            user.setAddress("sd");
            Result result = userService.insertUser(user);
            return result;
        } catch (Exception e) {
            return new Result(500,"error",e);
        }
    }
}
