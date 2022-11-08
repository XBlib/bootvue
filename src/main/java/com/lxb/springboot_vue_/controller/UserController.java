package com.lxb.springboot_vue_.controller;


import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.common.Constans;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.pojo.User;
import com.lxb.springboot_vue_.pojo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constans.CODE_600, "参数错误");
        }
        return userService.login(userDTO);
    }
    //注册用户信息
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constans.CODE_600, "参数错误");
        }
        User user = new User();
        BeanUtil.copyProperties(userDTO,user);
        return userService.regUser(user);
    }
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        return Result.success(userService.getOneUser(username));
    }
    //查询用户信息
    @GetMapping("/list")
    public Result getUserList() {
        return Result.success(userService.list());
    }
    //根据id删除用户信息
    @DeleteMapping("/del/{id}")
    public Result delUser(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }
    //根据id批量删除
    @PostMapping("/delBatch")
    public Result delUser(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }
    //更新或保存用户信息
    @PostMapping("/save")
    public Result saveUser(@RequestBody User user) {
        userService.saveOrUpdate(user);
        return Result.success(userService.getOneUser(user.getUsername()));
    }
    //查询用户信息(分页)
    @GetMapping("/page")
    public Result userOfPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String username,
                                  @RequestParam(defaultValue = "") String phone
                                         ){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        queryWrapper.like("phone",phone);
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(page,queryWrapper));
    }
    //excel数据导出
    @GetMapping("/export")
    public void expUserExcel(HttpServletResponse response) throws IOException {
        List<User> userList = userService.list();
        //写到浏览器
        //默认生成xls格式
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //设置别名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.setOnlyAlias(true);
        //设置表头
        Class<User> userClass = User.class;
        int length = userClass.getDeclaredFields().length;
        writer.merge(length - 3,"用户信息");
        //一次性写出
        writer.write(userList,true);
        //设置响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filePath = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + filePath + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }
    //Excel数据导入
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("昵称","nickname");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("地址","address");
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        return Result.success();
    }


}
