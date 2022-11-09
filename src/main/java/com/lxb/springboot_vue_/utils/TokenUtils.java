package com.lxb.springboot_vue_.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author XBlib
 * @version 1.0
 */
@Component
public class TokenUtils {
    private static UserService staticUserService;
    @Autowired
    private UserService userService;
    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }
    /**
     * 生成token
     */
    public static String getToken(String userId, String sign) {
        return JWT.create().withAudience(userId)//id作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时后token过期
                .sign(Algorithm.HMAC256(sign));//password 作为 token 的 秘钥
    }

    /**
     * 根据token获取当前用户信息
     * @return  User
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(!StrUtil.isBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;

    }
}
