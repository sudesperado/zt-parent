package com.zhitong.loginserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhitong.loginserver.entity.Role;
import com.zhitong.loginserver.entity.User;
import com.zhitong.loginserver.entity.UserRoleRel;
import com.zhitong.loginserver.mapper.RoleMapper;
import com.zhitong.loginserver.mapper.UserMapper;
import com.zhitong.loginserver.mapper.UserRoleRelMapper;
import com.zhitong.loginserver.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhitong.loginserver.service.IUserRoleRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelMapper userRoleRelMapper;


    /**
     * 查询角色列表
     * @param username
     * @return
     */
    @Override
    public Set<String> findRoleByUsername(String username) {

        User user = new User();
        user.setUsername(username);
        User sysUser = userMapper.selectOne(new QueryWrapper<>(user));

        QueryWrapper<UserRoleRel> userRoleRelQueryWrapper = new QueryWrapper<>();
        userRoleRelQueryWrapper.eq("user_id",sysUser.getId());
        List<UserRoleRel> userRoleRelList = userRoleRelMapper.selectList(userRoleRelQueryWrapper);

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        List<String> roleIdList = userRoleRelList.stream().distinct().map(UserRoleRel::getRoleId).collect(Collectors.toList());
        return new HashSet<>(roleIdList);
    }
}
