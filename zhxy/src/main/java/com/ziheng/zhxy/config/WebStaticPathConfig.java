package com.ziheng.zhxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 静态资源配置类
@Configuration
public class WebStaticPathConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    String updateFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file:" + updateFilePath + "/upload/";

        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:D:/newjavaproject/projectFileSavePath/zhxy/userAvatar/upload/");
                .addResourceLocations(path);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 配置knife4j
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
