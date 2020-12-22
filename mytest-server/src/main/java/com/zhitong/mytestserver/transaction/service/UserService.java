package com.zhitong.mytestserver.transaction.service;


import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.transaction.entity.User;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.transaction.service
 * @Description:
 * @date Date : 2020年11月27日 14:25
 */
public interface UserService {
    Result insertUser(User user);
}
