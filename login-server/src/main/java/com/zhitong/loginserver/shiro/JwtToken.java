package com.zhitong.loginserver.shiro;

import com.zhitong.loginserver.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.loginserver.shiro
 * @Description:
 * @date Date : 2021年01月11日 15:30
 */
public class JwtToken implements AuthenticationToken {
    private static final long serialVersionUID = -8451637096112402805L;
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
       return JwtUtil.getPassword(token);
    }
}
