package com.zhitong.loginserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhitong.loginserver.entity.Result;
import com.zhitong.loginserver.entity.User;
import com.zhitong.loginserver.mapper.UserMapper;
import com.zhitong.loginserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhitong.loginserver.util.JwtUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    private static final String ACCESS_KEY = "access_key";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

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
        String sign = JwtUtil.sign(username, password);
        return Result.newInstance().success("操作成功",sign);
    }

    @Override
    public Result insertSelective(User user) {
        //将uuid设置为密码盐值
        String salt = UUID.randomUUID().toString().replaceAll("-","");
        ByteSource salt2 = ByteSource.Util.bytes(salt);
        SimpleHash simpleHash = new SimpleHash("MD5", user.getPassword(), salt2, 8);
        user.setPassword(simpleHash.toHex()).setSalt(salt);
        userMapper.insert(user);
        return Result.newInstance().success("200",user);
    }

    @Override
    public Result<JSONObject> toLogin(User loginUser) {

        String userName = loginUser.getUsername();
        String passWord = loginUser.getPassword();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",userName);
        List<User> users = userMapper.selectList(userQueryWrapper);
        User user = users.get(0);
        if (user == null) {
            return Result.newInstance().filed("用户不存在");
        }
        //我的密码是使用uuid作为盐值加密的，所以这里登陆时候还需要做一次对比
        SimpleHash simpleHash = new SimpleHash("MD5", passWord, ByteSource.Util.bytes(user.getSalt()), 8);
        if(!simpleHash.toHex().equals(user.getPassword())){
            return Result.newInstance().filed("密码不正确");
        }
        // 生成token
        String token = JwtUtil.sign(userName, passWord);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(user),2, TimeUnit.HOURS);
        JSONObject obj = new JSONObject();
        obj.put("userId",user.getId());
        obj.put("username",user.getUsername());
        obj.put("token", token);
        return Result.newInstance().success("登录成功",obj);
    }

    @Override
    public Result<JSONObject> loginOut(String token) {
        Boolean bool = redisTemplate.delete(token);
        if (bool){
            return Result.newInstance().success("退出成功",bool);
        }else {
            return Result.newInstance().filed("退出失败");
        }
    }
}
