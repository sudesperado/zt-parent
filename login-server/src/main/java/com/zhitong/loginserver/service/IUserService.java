package com.zhitong.loginserver.service;

import com.alibaba.fastjson.JSONObject;
import com.zhitong.loginserver.entity.Result;
import com.zhitong.loginserver.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2020-12-25
 */
public interface IUserService extends IService<User> {

    Result loginIn(String username, String password);

    Result insertSelective(User user);

    Result<JSONObject> toLogin(User loginUser);
}
