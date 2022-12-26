package com.lxb.springboot_vue_.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.pojo.Role;
import com.lxb.springboot_vue_.pojo.RoleMenu;
import com.lxb.springboot_vue_.pojo.User;
import com.lxb.springboot_vue_.pojo.dto.UserDTO;

import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
public interface RoleService extends IService<Role> {
    Role getOneRole(String roleName);

    Result setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
