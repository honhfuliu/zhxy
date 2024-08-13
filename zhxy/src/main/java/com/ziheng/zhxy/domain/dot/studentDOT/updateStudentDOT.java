package com.ziheng.zhxy.domain.dot.studentDOT;

import lombok.Data;

import java.util.Date;

@Data
public class updateStudentDOT {
    private Integer sid;
    private String sName;
    private String className;
    private String gradeName;
    private Integer classId;
    private Integer gradeId;
    private Date enrollmentDate;
    private String username;
}
