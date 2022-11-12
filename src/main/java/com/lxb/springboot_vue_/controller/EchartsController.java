package com.lxb.springboot_vue_.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author XBlib
 * @version 1.0
 */

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private UserService userService;

    @GetMapping("/getMD")
    public Result getMonthData() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", Arrays.asList("第一季度","第二季度","第三季度","第三季度"));
        map.put("y",Arrays.asList(10,29,2,36));
        return Result.success(map);
    }

    @PostMapping("/members")
    public Result members() {

        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default:break;
            }
        }

        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }
}
