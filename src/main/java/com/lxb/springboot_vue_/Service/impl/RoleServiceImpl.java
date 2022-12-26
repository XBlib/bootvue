package com.lxb.springboot_vue_.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.RoleService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.mapper.RoleMapper;
import com.lxb.springboot_vue_.mapper.RoleMenuMapper;
import com.lxb.springboot_vue_.pojo.Role;
import com.lxb.springboot_vue_.pojo.RoleMenu;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Role getOneRole(String roleName) {
        return roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName,roleName));
    }
    @Transactional
    @Override
    public Result setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(roleMenuQueryWrapper);
        roleMenuMapper.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.getRoleMenu(roleId);
    }
}
