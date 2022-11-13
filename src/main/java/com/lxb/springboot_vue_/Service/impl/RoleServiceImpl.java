package com.lxb.springboot_vue_.Service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.RoleService;
import com.lxb.springboot_vue_.mapper.RoleMapper;
import com.lxb.springboot_vue_.pojo.Role;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author XBlib
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Role getOneRole(String roleName) {
        return roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName,roleName));
    }
}
