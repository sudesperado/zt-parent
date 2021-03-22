package com.zhitong.mytestserver.filter;

import com.alibaba.fastjson.JSON;
import com.zhitong.mytestserver.model.User;
import com.zhitong.mytestserver.model.UserInfoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.filter
 * @Description:
 * @date Date : 2021年01月26日 14:47
 */
@WebFilter("/*")
@Component
public class TransmitUserInfoFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TransmitUserInfoFilter.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String token = request.getHeader("Token");
            String userStr =(String) redisTemplate.opsForValue().get(token);
            User user = JSON.parseObject(userStr, User.class);
            UserInfoContext.setUser(user);
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            log.error("过滤器执行失败：{}",e);
        }finally {
            log.info("清除用户信息");
            UserInfoContext.clearUser();
        }
    }
}
