package com.zhitong.loginserver.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.zhitong.loginserver.entity.User;
import com.zhitong.loginserver.mapper.UserMapper;
import com.zhitong.loginserver.service.IResourceService;
import com.zhitong.loginserver.service.IRoleService;
import com.zhitong.loginserver.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.loginserver.shiro
 * @Description:
 * @date Date : 2021年01月11日 15:32
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IResourceService resourceService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 访问控制。比如某个用户是否具有某个操作的使用权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user  = (User) principalCollection.getPrimaryPrincipal();
        if (user == null) {
            log.error("授权失败，用户信息为空！！！");
            return null;
        }
        try {
            //获取用户角色集
            Set<String> listRole= roleService.findRoleByUsername(user.getUsername());
            simpleAuthorizationInfo.addRoles(listRole);

            //通过角色获取权限集
            for (String role : listRole) {
                Set<String> permission= resourceService.findPermissionByRole(role);
                simpleAuthorizationInfo.addStringPermissions(permission);
            }
            return simpleAuthorizationInfo;
        } catch (Exception e) {
            log.error("授权失败，请检查系统内部错误!!!", e);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户身份识别(登录")
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 校验token有效性
        String token = (String) authenticationToken.getPrincipal();
        String username = JwtUtil.getUsername(token);
        if (Strings.isNullOrEmpty(username)) {
            throw new AuthenticationException("token非法无效!");
        }
        //查询用户信息 验证用户账号/密码
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(userQueryWrapper);
        User user = new User();
        if (!CollectionUtils.isEmpty(users)){
            user = users.get(0);
        }
//        User sysUser = userService.selectUserOne(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());
    }
}
