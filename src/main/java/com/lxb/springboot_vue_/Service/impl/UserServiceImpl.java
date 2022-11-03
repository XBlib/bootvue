package com.lxb.springboot_vue_.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.mapper.UserMapper;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author XBlib
 * @version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;



}
