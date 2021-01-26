package com.zhitong.loginserver.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zhitong.loginserver.entity.CommonConstant;
import com.zhitong.loginserver.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.loginserver.filter
 * @Description:
 * @date Date : 2021年01月25日 14:56
 */
@Component
public class MyFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getHeader(CommonConstant.ACCESS_TOKEN);
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){
                log.error("验证失败:{}",e);
            }
            return null;
        }else {
            String o =(String) redisTemplate.opsForValue().get(accessToken.toString());
            User user = JSON.parseObject(o, User.class);
            if (user==null){
                log.warn("token已失效");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                try {
                    ctx.getResponse().getWriter().write("token is invalidate");
                }catch (Exception e){
                    log.error("验证失败:{}",e);
                }
                return null;
            }
        }
        log.info("ok");
        return null;
    }
}
