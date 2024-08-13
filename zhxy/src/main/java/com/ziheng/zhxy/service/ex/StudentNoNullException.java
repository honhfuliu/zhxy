package com.ziheng.zhxy.service.ex;

// 学生信息不存在异常
public class StudentNoNullException extends ServiceException{
    public StudentNoNullException() {
    }

    public StudentNoNullException(String message) {
        super(message);
    }

    public StudentNoNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNoNullException(Throwable cause) {
        super(cause);
    }

    public StudentNoNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
