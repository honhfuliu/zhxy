package com.ziheng.zhxy.service.ex;

// 班级名称已存在异常
public class TClassNameDuplicateException extends ServiceException{
    public TClassNameDuplicateException() {
    }

    public TClassNameDuplicateException(String message) {
        super(message);
    }

    public TClassNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TClassNameDuplicateException(Throwable cause) {
        super(cause);
    }

    public TClassNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
