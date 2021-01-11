package com.zhitong.loginserver.controller;

import com.zhitong.loginserver.entity.Result;
import com.zhitong.loginserver.entity.User;
import com.zhitong.loginserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.loginserver.controller
 * @Description:
 * @date Date : 2021年01月11日 14:52
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private IUserService userService;

    @CrossOrigin
    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public Result loginIn(@RequestBody User user){
        return  userService.insertSelective(user);
    }
}
