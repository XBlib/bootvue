package com.lxb.springboot_vue_.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.common.Constans;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.exception.ServiceException;
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
    public User login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one = null;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constans.CODE_500,"系统错误");
        }
        if(one != null) {
            BeanUtil.copyProperties(one,userDTO,true);
            return one;
            } else {
            throw new ServiceException(Constans.CODE_600,"用户名和密码错误");
        }



    }

    @Override
    public Result regUser(User user) {
        QueryWrapper<User> qw = new QueryWrapper<User>().eq("username", user.getUsername());
        User one = getOne(qw);
        if(one != null) {
            throw new ServiceException(Constans.CODE_600,"该账号已注册");
        }
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            throw new ServiceException(Constans.CODE_600,"注册失败");
        }
        return Result.success();
    }

    @Override
    public User getOneUser(String username) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().like(User::getUsername, username));
    }
}
