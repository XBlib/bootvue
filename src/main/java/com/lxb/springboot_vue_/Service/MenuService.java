package com.lxb.springboot_vue_.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxb.springboot_vue_.pojo.Menu;

/**
 * @author XBlib
 * @version 1.0
 */
public interface MenuService extends IService<Menu> {
    Menu getOneRole(String menuName);
}
