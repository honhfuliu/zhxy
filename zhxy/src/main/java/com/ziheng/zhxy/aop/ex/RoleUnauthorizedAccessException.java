package com.ziheng.zhxy.aop.ex;

// 无权限操作异常
public class RoleUnauthorizedAccessException extends RuntimeException{
    public RoleUnauthorizedAccessException() {
    }

    public RoleUnauthorizedAccessException(String message) {
        super(message);
    }

    public RoleUnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleUnauthorizedAccessException(Throwable cause) {
        super(cause);
    }

    public RoleUnauthorizedAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
