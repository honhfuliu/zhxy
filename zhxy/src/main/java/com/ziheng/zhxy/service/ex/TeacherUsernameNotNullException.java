package com.ziheng.zhxy.service.ex;

// 老师用户与老师信息绑定用户信息不存在异常
public class TeacherUsernameNotNullException extends ServiceException{
    public TeacherUsernameNotNullException() {
    }

    public TeacherUsernameNotNullException(String message) {
        super(message);
    }

    public TeacherUsernameNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeacherUsernameNotNullException(Throwable cause) {
        super(cause);
    }

    public TeacherUsernameNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
