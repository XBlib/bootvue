package com.lxb.springboot_vue_.Service.impl;

import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.mapper.UserMapper;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李新波
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }
    @Override
    public String delUser(Integer id) {
        return userMapper.delUser(id).toString();
    }
    @Override
    public String saveUser(User user) {
        //id为空表示新增，不为空表示更新
        if(user.getId() == null) {
            return userMapper.saveUser(user).toString();
        } else {
            return userMapper.updateUser(user).toString();
        }
    }

    @Override
    public Map<String, Object> userOfPage(Integer pageNum, Integer pageSize, String username,String phone) {
        Map userMap = new HashMap();
        int totalSize = userMapper.getUserList().size();
        pageNum = (pageNum - 1) * pageSize;
        List<User> userPage = userMapper.getUserPage(pageNum, pageSize,username,phone);
        if(!username.equals("%%") || !phone.equals("")){
            totalSize = userPage.size();
        }
        userMap.put("total",totalSize);
        userMap.put("data",userPage);
        return userMap;
    }
}
