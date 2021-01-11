package com.zhitong.loginserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhitong.loginserver.entity.Resource;
import com.zhitong.loginserver.entity.RoleResourceRel;
import com.zhitong.loginserver.mapper.ResourceMapper;
import com.zhitong.loginserver.mapper.RoleResourceRelMapper;
import com.zhitong.loginserver.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceRelMapper roleResourceRelMapper;

    @Override
    public Set<String> findPermissionByRole(String role) {

        QueryWrapper<RoleResourceRel> roleResourceRelQueryWrapper = new QueryWrapper<>();
        roleResourceRelQueryWrapper.eq("role_id",role);
        List<RoleResourceRel> roleResourceRelList = roleResourceRelMapper.selectList(roleResourceRelQueryWrapper);
        List<Long> resourceList = roleResourceRelList.stream().distinct().map(RoleResourceRel::getId).collect(Collectors.toList());
        Set<String> resIdSet = new HashSet<>();
        resourceList.stream().forEach(item->resIdSet.add(item.toString()));
        return resIdSet;
    }
}
