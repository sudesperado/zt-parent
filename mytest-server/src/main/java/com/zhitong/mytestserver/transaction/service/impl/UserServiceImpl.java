package com.zhitong.mytestserver.transaction.service.impl;


import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.transaction.dao.UserMapper;
import com.zhitong.mytestserver.transaction.entity.User;
import com.zhitong.mytestserver.transaction.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.transaction.service.impl
 * @Description:
 * @date Date : 2020年11月27日 14:26
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    //@Transactional
    public Result insertUser(User user) {
        System.out.println("shenmewanyi.....");
        validate(user);
        int i = user.getAge()/0;
        return new Result(200,"success","bingo!");
    }

    private void validate(User user){
        userMapper.insert(user);
    }
}
