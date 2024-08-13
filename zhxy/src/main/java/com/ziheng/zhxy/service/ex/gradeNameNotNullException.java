package com.ziheng.zhxy.service.ex;

// 年级为空异常
public class gradeNameNotNullException extends ServiceException{
    public gradeNameNotNullException() {
    }

    public gradeNameNotNullException(String message) {
        super(message);
    }

    public gradeNameNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public gradeNameNotNullException(Throwable cause) {
        super(cause);
    }

    public gradeNameNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
