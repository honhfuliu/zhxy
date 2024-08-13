package com.ziheng.zhxy.service.ex;

// 班级信息不存在异常
public class TClassNoNullException extends ServiceException{
    public TClassNoNullException() {
    }

    public TClassNoNullException(String message) {
        super(message);
    }

    public TClassNoNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public TClassNoNullException(Throwable cause) {
        super(cause);
    }

    public TClassNoNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
