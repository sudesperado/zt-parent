package com.zhitong.loginserver.service;

import com.zhitong.loginserver.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2021-01-11
 */
public interface IRoleService extends IService<Role> {

    Set<String> findRoleByUsername(String username);
}
