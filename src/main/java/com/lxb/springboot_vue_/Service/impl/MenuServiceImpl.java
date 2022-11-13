package com.lxb.springboot_vue_.Service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.MenuService;
import com.lxb.springboot_vue_.mapper.MenuMapper;
import com.lxb.springboot_vue_.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author XBlib
 * @version 1.0
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public Menu getOneRole(String menuName) {
        return menuMapper.selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getMenuName,menuName));
    }
}
