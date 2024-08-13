package com.ziheng.zhxy.service.ex;

// 年级不存在异常
public class gradeNotNullException extends ServiceException{
    public gradeNotNullException() {
    }

    public gradeNotNullException(String message) {
        super(message);
    }

    public gradeNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public gradeNotNullException(Throwable cause) {
        super(cause);
    }

    public gradeNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
