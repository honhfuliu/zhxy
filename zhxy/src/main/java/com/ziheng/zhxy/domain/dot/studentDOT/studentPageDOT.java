package com.ziheng.zhxy.domain.dot.studentDOT;

import lombok.Data;

import java.util.Date;

// 学生分页查询数据返回类
@Data
public class studentPageDOT {
    private Integer sid; // id
    private String sName; // 学生名称
    private String username; // 账号名称
    private String className; // 班级名称
    private String gName; // 年级名称
    private Date enrollmentDate; // 入学时间
    private String phone; //手机号
    private String email; // 邮箱
    private Integer gender; // 性别
}
