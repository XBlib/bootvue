package com.lxb.springboot_vue_.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxb.springboot_vue_.Service.RoleService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.mapper.RoleMenuMapper;
import com.lxb.springboot_vue_.pojo.Role;
import com.lxb.springboot_vue_.pojo.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/roleName/{roleName}")
    public Result findOne(@PathVariable String roleName) {
        return Result.success(roleService.getOneRole(roleName));
    }
    //查询角色信息
    @GetMapping("/list")
    public Result getRoleList() {
        return Result.success(roleService.list());
    }
    //根据id删除角色信息
    @DeleteMapping("/del/{id}")
    public Result delRole(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }
    //根据id批量删除
    @PostMapping("/delBatch")
    public Result delRole(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeBatchByIds(ids));
    }
    //更新或保存用户信息
    @PostMapping("/save")
    public Result saveRole(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success(roleService.getOneRole(role.getRoleName()));
    }
    //查询用户信息(分页)
    @GetMapping("/page")
    public Result roleOfPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String roleName
    ){
        IPage<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("role_name",roleName);
        queryWrapper.orderByDesc("id");
        IPage<Role> page1 = roleService.page(page, queryWrapper);
        return Result.success(page1);
    }
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds) {
        return roleService.setRoleMenu(roleId,menuIds);

    }
    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        List<Integer> roleMenu = roleService.getRoleMenu(roleId);
        return Result.success(roleMenu);

    }
}
