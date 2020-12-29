package com.zhitong.mytestserver.service.impl;

import com.zhitong.mytestserver.service.MyUserService;
import org.springframework.stereotype.Service;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.service.impl
 * @Description:
 * @date Date : 2020年12月28日 10:51
 */
@Service
public class MyUserServiceImpl implements MyUserService {

    @Override
    public String getUser(){
        return "pylu";
    }

}
