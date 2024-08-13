package com.ziheng.zhxy.config;

import com.ziheng.zhxy.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;


// 拦截器白名单配置类
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> asList = Arrays.asList(
                "/user/reg",
                "/user/login",
                "/static/**",
                "/upload/**",
                "/favicon.ico",
                "/error",
                "/swagger-resources/**",
                "/webjars/**",
                "/v3/**",
                "/swagger-ui.html/**",
                "/api",
                "/api-docs",
                "/api-docs/**",
                "/doc.html/**",
//                "/**",
                "/doc.html#/**");
        System.out.println(asList.size());
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(asList).addPathPatterns("/**");
    }
}
