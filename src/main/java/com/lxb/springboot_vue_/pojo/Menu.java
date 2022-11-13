package com.lxb.springboot_vue_.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
@Data
@TableName("sys_menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String menuName;
    private String path;
    private String icon;
    private String description;
    @TableField(exist = false)
    private List<Menu> children;
    private Integer pid;
}
