package com.lxb.springboot_vue_.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxb.springboot_vue_.Service.MenuService;
import com.lxb.springboot_vue_.common.Constans;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.mapper.DictMapper;
import com.lxb.springboot_vue_.pojo.Dict;
import com.lxb.springboot_vue_.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XBlib
 * @version 1.0
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private DictMapper dictMapper;
    @GetMapping("/MenuName/{MenuName}")
    public Result findOne(@PathVariable String MenuName) {
        return Result.success(menuService.getOneRole(MenuName));
    }
    //查询角色信息
    @GetMapping()
    public Result getMenuList(@RequestParam(defaultValue = "") String menuName) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("menu_name",menuName);
        //查询所有数据
        List<Menu> menuList = menuService.list(queryWrapper);
        List<Menu> parentNone = menuList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentNone) {
            menu.setChildren(menuList.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return Result.success(parentNone);
    }
    //根据id删除角色信息
    @DeleteMapping("/del/{id}")
    public Result delMenu(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }
    //根据id批量删除
    @PostMapping("/delBatch")
    public Result delMenu(@RequestBody List<Integer> ids) {
        return Result.success(menuService.removeBatchByIds(ids));
    }
    //更新或保存用户信息
    @PostMapping("/save")
    public Result saveMenu(@RequestBody Menu menu) {
        Menu oneRole = menuService.getOneRole(menu.getMenuName());
        if(oneRole != null) {
            return Result.success("该名称已存在");
        }
        menuService.saveOrUpdate(menu);
        return Result.success();
    }
    //查询用户信息(分页)
    @GetMapping("/page")
    public Result menuOfPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String menuName
    ){
        IPage<Menu> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("menu_name",menuName);
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(page, queryWrapper));
    }

    @GetMapping("/icons")
    public Result getDict() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type","icon");
        List<Dict> dicts = dictMapper.selectList(dictQueryWrapper);
        return Result.success(dicts);
    }
}
