package com.ziheng.zhxy.service.ex;

// 年级对象时间为空异常
public class gradeTimeNotNullException extends ServiceException{
    public gradeTimeNotNullException() {
    }

    public gradeTimeNotNullException(String message) {
        super(message);
    }

    public gradeTimeNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public gradeTimeNotNullException(Throwable cause) {
        super(cause);
    }

    public gradeTimeNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
