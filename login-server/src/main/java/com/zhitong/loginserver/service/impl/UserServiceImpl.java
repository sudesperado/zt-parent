package com.zhitong.loginserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhitong.loginserver.entity.Result;
import com.zhitong.loginserver.entity.User;
import com.zhitong.loginserver.mapper.UserMapper;
import com.zhitong.loginserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result loginIn(String username, String password) {
        //验证用户账号/密码
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(userQueryWrapper);
        if (CollectionUtils.isEmpty(users)){
            return Result.newInstance().filed("当前登录账号不存在!");
        }
        User user = users.get(0);
        if (!user.getPassword().equals(password)){
            return Result.newInstance().filed("密码有误,请重新登录!");
        }

        //通过验证 生成token返回
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .setSubject(username)
                .claim("userInfo", JSON.toJSONString(user))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, password);
        String compact = jwtBuilder.compact();
        return Result.newInstance().success("操作成功",compact);
    }
}
