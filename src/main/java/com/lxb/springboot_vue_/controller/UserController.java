package com.lxb.springboot_vue_.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author XBlib
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //查询用户信息
    @GetMapping("/list")
    public List<User> getUserList() {
        return userService.list();
    }
    //根据id删除用户信息
    @DeleteMapping("/del/{id}")
    public Boolean delUser(@PathVariable Integer id) {
        return userService.removeById(id);
    }
    //根据id批量删除
    @PostMapping("/delBatch")
    public Boolean delUser(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }
    //更新或保存用户信息
    @PostMapping("/save")
    public Boolean saveUser(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }
    @GetMapping("/page")
    public IPage<User> userOfPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String username,
                                  @RequestParam(defaultValue = "") String phone
                                         ){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        queryWrapper.like("phone",phone);
        queryWrapper.orderByDesc("id");
        return userService.page(page,queryWrapper);

    }
}
