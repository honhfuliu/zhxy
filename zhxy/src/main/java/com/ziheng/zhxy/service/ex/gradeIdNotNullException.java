package com.ziheng.zhxy.service.ex;

// 年级id为空异常
public class gradeIdNotNullException extends ServiceException{
    public gradeIdNotNullException() {
    }

    public gradeIdNotNullException(String message) {
        super(message);
    }

    public gradeIdNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public gradeIdNotNullException(Throwable cause) {
        super(cause);
    }

    public gradeIdNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
