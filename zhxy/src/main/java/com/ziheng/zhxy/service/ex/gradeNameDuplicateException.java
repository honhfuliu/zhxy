package com.ziheng.zhxy.service.ex;

// 年级重复异常
public class gradeNameDuplicateException extends ServiceException{
    public gradeNameDuplicateException() {
    }

    public gradeNameDuplicateException(String message) {
        super(message);
    }

    public gradeNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public gradeNameDuplicateException(Throwable cause) {
        super(cause);
    }

    public gradeNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
