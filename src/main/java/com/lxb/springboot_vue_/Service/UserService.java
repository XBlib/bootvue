package com.lxb.springboot_vue_.Service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.pojo.User;
import com.lxb.springboot_vue_.pojo.dto.UserDTO;


/**
 * @author XBlib
 * @version 1.0
 */
public interface UserService extends IService<User> {

    Result login(UserDTO userDTO);

    Result regUser(User user);
    User getOneUser(String username);
}
