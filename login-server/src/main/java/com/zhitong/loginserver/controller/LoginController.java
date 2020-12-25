package com.zhitong.loginserver.controller;

import com.zhitong.loginserver.entity.Result;
import com.zhitong.loginserver.service.IUserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.loginserver.controller
 * @Description:
 * @date Date : 2020年12月25日 11:07
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/loginIn",method = RequestMethod.GET)
    public Result loginIn(@RequestParam("username") String username ,
                          @RequestParam("password") String password){

        //用户登录
        return userService.loginIn(username,password);
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public Result getUserInfo(HttpServletRequest httpServletRequest){
        //通过jwt解析用户信息
        try {
            String auth = httpServletRequest.getHeaders("Auth").nextElement();
            Claims zhangsan = Jwts.parser().setSigningKey("123456").parseClaimsJws(auth).getBody();
            System.out.println(zhangsan);
            return Result.newInstance().success("操作成功",zhangsan.toString());
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

}
