package com.lxb.springboot_vue_.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author XBlib
 * @version 1.0
 */
@Data
@TableName("sys_role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String roleName;
    private String description;
}
