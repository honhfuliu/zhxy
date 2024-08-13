package com.ziheng.zhxy.service.ex;

// 用户名不能为空异常
public class UsernameNotNullException extends ServiceException{
    public UsernameNotNullException() {
    }

    public UsernameNotNullException(String message) {
        super(message);
    }

    public UsernameNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotNullException(Throwable cause) {
        super(cause);
    }

    public UsernameNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
