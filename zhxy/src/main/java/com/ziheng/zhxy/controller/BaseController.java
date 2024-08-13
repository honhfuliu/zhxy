package com.ziheng.zhxy.controller;

import com.ziheng.zhxy.aop.ex.RoleUnauthorizedAccessException;
import com.ziheng.zhxy.controller.ex.FileUploadException;
import com.ziheng.zhxy.service.ex.*;
import com.ziheng.zhxy.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    public static  final int OK = 200; // 操作成功状态码

    @ExceptionHandler({ServiceException.class, FileUploadException.class, RoleUnauthorizedAccessException.class}) // 用于统一处理
    public JsonResult handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setState(5001);
            result.setMessage("用户名已存在");
        } else if (e instanceof InsertException) {
            result.setState(5002);
            result.setMessage("用户名注册时产生未知的异常，请于管理员联系!");
        } else if (e instanceof UnauthorizedAccessException) {
            result.setState(5003);
            result.setMessage("非法访问数据，你是不是有病呀非主流");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5004);
            result.setMessage("密码错误");
        }else if (e instanceof UserNotFoundException) {
            result.setState(5005);
            result.setMessage("用户数据不存在");
        }else if (e instanceof NotLoginException) {
            result.setState(5006);
            result.setMessage("用户未登录或者是登录信息已过期，请从新登录");
        }else if (e instanceof updateException) {
            result.setState(5007);
            result.setMessage("更新数据时产生未知异常，请于管理员联系");
        }else if (e instanceof NoPermissionException) {
            result.setState(5008);
            result.setMessage("你的权限不足无法访问");
        }else if (e instanceof UsernameNotNullException) {
            result.setState(5009);
            result.setMessage("用户名不能为空");
        }else if (e instanceof gradeNameDuplicateException) {
            result.setState(5010);
            result.setMessage("年级名称重复");
        }else if (e instanceof gradeNameNotNullException) {
            result.setState(5010);
            result.setMessage("年级名称不能为空");
        }else if (e instanceof gradeTimeNotNullException) {
            result.setState(5010);
            result.setMessage("年级创建或结束时间不能为空");
        }else if (e instanceof gradeNotNullException) {
            result.setState(5011);
            result.setMessage("年级不存在");
        }else if (e instanceof TClassNameDuplicateException) {
            result.setState(5012);
            result.setMessage("班级名称已存在");
        }else if (e instanceof TeacherUsernameNotNullException) {
            result.setState(5013);
            result.setMessage("绑定的老师账号不存在, 或者是该账号已绑定老师");
        }else if (e instanceof StudentUsernameNotNullException) {
            result.setState(5014);
            result.setMessage("绑定的学生账号不存在, 或者是该账号已绑定学生");
        }else if (e instanceof GradeNoClassException) {
            result.setState(5015);
            result.setMessage("年级中不存在该班级，请从新选择班级");
        }else if (e instanceof StudentNoNullException) {
            result.setState(5016);
            result.setMessage("学生信息不存在");
        }else if (e instanceof RoleUnauthorizedAccessException) {
            result.setState(8888);
            result.setMessage("你无权限进行操作");
        }

        return result;
    }
}
