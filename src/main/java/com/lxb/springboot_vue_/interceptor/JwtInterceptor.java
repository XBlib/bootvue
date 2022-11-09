package com.lxb.springboot_vue_.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.lxb.springboot_vue_.Service.UserService;
import com.lxb.springboot_vue_.common.Constans;
import com.lxb.springboot_vue_.exception.ServiceException;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XBlib
 * @version 1.0
 */

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射在方法上则直接通过
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)) {
            throw new ServiceException(Constans.CODE_600,"请重新登录");
        }
        //获取token中的userid
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constans.CODE_600,"出错了...");
        }
        //根据id查询数据库
        User user = userService.getById(userId);
        if(user == null) {
            throw new ServiceException(Constans.CODE_600,"出错了，请重新登录");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);//验证token
        } catch (Exception e) {
            throw new ServiceException(Constans.CODE_600,"出错啦，请重新登录");
        }
        return true;
    }

}
