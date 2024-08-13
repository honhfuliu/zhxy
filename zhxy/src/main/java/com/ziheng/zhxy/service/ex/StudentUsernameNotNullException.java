package com.ziheng.zhxy.service.ex;

// 学生绑定账号信息不存在异常
public class StudentUsernameNotNullException extends ServiceException{
    public StudentUsernameNotNullException() {
    }

    public StudentUsernameNotNullException(String message) {
        super(message);
    }

    public StudentUsernameNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentUsernameNotNullException(Throwable cause) {
        super(cause);
    }

    public StudentUsernameNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
