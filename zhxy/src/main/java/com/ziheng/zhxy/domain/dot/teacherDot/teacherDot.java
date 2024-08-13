package com.ziheng.zhxy.domain.dot.teacherDot;

import lombok.Data;

import java.util.Date;

@Data
public class teacherDot {
    private Integer tId;
    private String tName;
    private String course;
    private String username;
    private Date entryTime;
    private String degree;
}
