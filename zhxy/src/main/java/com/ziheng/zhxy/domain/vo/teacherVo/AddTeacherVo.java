package com.ziheng.zhxy.domain.vo.teacherVo;

import lombok.Data;

import java.util.Date;

@Data
public class AddTeacherVo {
    private Integer tId;
    private String tName;
    private String username;
    private String course;
    private String degree;
    private Date entryTime;


}
