package com.lxb.springboot_vue_.Service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.mapper.UserMapper;
import com.lxb.springboot_vue_.pojo.User;
import com.lxb.springboot_vue_.pojo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author XBlib
 * @version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        List<User> list = list(queryWrapper);
        return list.size() != 0;
    }
}
