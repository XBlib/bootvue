package com.lxb.springboot_vue_.Service;

import com.lxb.springboot_vue_.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author 李新波
 * @version 1.0
 */
public interface UserService {
    List<User> getUserList();
    String delUser(Integer id);
    String saveUser(User user);

    Map<String, Object> userOfPage(Integer pageNum, Integer pageSize,String username,String phone);
}
