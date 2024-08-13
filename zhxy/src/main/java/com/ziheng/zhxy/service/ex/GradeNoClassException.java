package com.ziheng.zhxy.service.ex;

// 年级中不包含班级异常处理
public class GradeNoClassException extends ServiceException{
    public GradeNoClassException() {
    }

    public GradeNoClassException(String message) {
        super(message);
    }

    public GradeNoClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public GradeNoClassException(Throwable cause) {
        super(cause);
    }

    public GradeNoClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
