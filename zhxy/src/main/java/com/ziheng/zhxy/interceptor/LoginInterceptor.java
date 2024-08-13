package com.ziheng.zhxy.interceptor;

import com.ziheng.zhxy.service.ex.NotLoginException;
import com.ziheng.zhxy.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;


public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截路径是：" + request.getRequestURI());
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
//            System.out.println("OPTIONS请求，放行");
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token == null) {
//            return false;
            throw new NotLoginException("用户未登录");
//             new JsonResult<Void>(50001, "用户未登录");
        }
        boolean verifyToken = TokenUtil.verifyToken(token);

        if (!verifyToken) {
            throw new NotLoginException("用户未登录，或者用户登录信息过期，请从新登录");
        }
        return true;

    }
}
