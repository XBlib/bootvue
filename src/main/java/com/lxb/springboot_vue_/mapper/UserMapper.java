package com.lxb.springboot_vue_.mapper;

import com.lxb.springboot_vue_.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 李新波
 * @version 1.0
 */
@Mapper
public interface UserMapper {
    @Select("select * from sys_user")
    List<User> getUserList();
    Integer updateUser(User user);
    Integer delUser(@Param("id") Integer id);
    Integer saveUser(User user);
    List<User> getUserPage(@Param("pageNum") Integer pageNum,
                           @Param("pageSize") Integer pageSize,
                           @Param("username") String username,
                           @Param("phone") String phone
                           );
}
