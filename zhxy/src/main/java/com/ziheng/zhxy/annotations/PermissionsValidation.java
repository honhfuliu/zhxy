package com.ziheng.zhxy.annotations;
//自定义注解：权限验证

import com.ziheng.zhxy.aop.roleAuthorizationAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PermissionsValidation {

    String[] values() default {};

}
