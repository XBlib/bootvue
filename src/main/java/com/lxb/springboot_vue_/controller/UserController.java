package com.lxb.springboot_vue_.controller;

import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 李新波
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
        return userService.getUserList();
    }
    //删除用户信息
    @PostMapping("/del")
    public String delUser(@RequestParam("id") Integer id) {
        return userService.delUser(id);
    }
    //更新或保存用户信息
    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    @GetMapping("/page")
    public Map<String,Object> userOfPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String username,
                                         @RequestParam String phone
                                         ){
        username = "%" + username + "%";
        return userService.userOfPage(pageNum,pageSize,username,phone);

    }
}
