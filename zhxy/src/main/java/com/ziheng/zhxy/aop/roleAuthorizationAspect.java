package com.ziheng.zhxy.aop;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.aop.ex.RoleUnauthorizedAccessException;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class roleAuthorizationAspect {
//    private Logger logger = LoggerFactory.getLogger(roleAuthorizationAspect.class);

    @Around("@annotation(com.ziheng.zhxy.annotations.PermissionsValidation)")
    public Object permissionsValidationAop(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 获取注解方法中的value
//        PermissionsValidation annotation = joinPoint.getTarget().getClass()
//                .getMethod(joinPoint.getSignature().getName())
//                .getAnnotation(PermissionsValidation.class);
//        String[] values = annotation.values();
//        System.out.println(Arrays.toString(values));
//        System.out.println("面向切面运行");
//        return joinPoint.proceed();
        // 获取方法的参数类型列表
        Class[] parameterTypes = Arrays.stream(joinPoint.getArgs())
                .map(Object::getClass)
                .toArray(Class[]::new);

        // 获取注解方法
        Method method = joinPoint.getTarget().getClass()
                .getMethod(joinPoint.getSignature().getName(), parameterTypes);

        // 获取注解
        PermissionsValidation annotation = method.getAnnotation(PermissionsValidation.class);

//        if (annotation != null) {
//            System.out.println(Arrays.toString(values));
//            System.out.println("面向切面运行");
//        }
        String[] values = annotation.values();
        List<String> list = Arrays.asList(values); // 可以访问该方法权限列表
        System.out.println(list);
        // 获取请求头
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取具体的请求头信息
        String token = request.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);

        if (list.contains(tokenTuser.getRole())){
            return joinPoint.proceed();
        }else  {
            throw new RoleUnauthorizedAccessException("你无权限进行操作");
        }
    }
}

