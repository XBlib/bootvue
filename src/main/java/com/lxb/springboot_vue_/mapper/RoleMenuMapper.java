package com.lxb.springboot_vue_.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxb.springboot_vue_.pojo.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    void setRoleMenu(Integer roleId,List<Integer> menuIds);
    List<Integer> getRoleMenu(Integer roleId);
}
