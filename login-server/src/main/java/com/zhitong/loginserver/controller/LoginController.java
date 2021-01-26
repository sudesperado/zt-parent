package com.zhitong.loginserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhitong.loginserver.entity.Result;
import com.zhitong.loginserver.entity.User;
import com.zhitong.loginserver.service.IUserService;
import com.zhitong.loginserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.loginserver.controller
 * @Description:
 * @date Date : 2020年12月25日 11:07
 */
@RestController
public class LoginController {

    private static final String ACCESS_KEY = "access_key";

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/userLogin",method = RequestMethod.GET)
    public Result loginIn(@RequestParam("username") String username ,
                          @RequestParam("password") String password){

        //用户登录
        return userService.loginIn(username,password);
    }

    @CrossOrigin
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public Result getUserInfo(HttpServletRequest httpServletRequest){
        //通过jwt解析用户信息
        try {
//            String auth = httpServletRequest.getHeaders(ACCESS_KEY).nextElement();
////            Claims zhangsan = Jwts.parser().setSigningKey(ACCESS_KEY).parseClaimsJws(auth).getBody();
//            String username = JwtUtil.getUsername(auth);
//            System.out.println(username);
            System.out.println("===========================");
            return Result.newInstance().success("操作成功","jj");
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 登录
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/userLogin")
    @ResponseBody
    public Result<JSONObject> toLogin(@RequestBody User loginUser) throws Exception {
        return userService.toLogin(loginUser);
    }

}
