package com.lxb.springboot_vue_.pojo.dto;

import lombok.Data;

/**
 * @author XBlib
 * @version 1.0
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
