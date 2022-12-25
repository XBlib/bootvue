package com.lxb.springboot_vue_.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author XBlib
 * @version 1.0
 */
@Data
@TableName("sys_dict")
public class Dict {
    private String name;
    private String value;
    private String type;
}
