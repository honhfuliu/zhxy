package com.ziheng.zhxy.domain.vo.studentVo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

// 学生添加信息接收对象
@Data
public class addStudentVO {
    private Integer sid;
    private String sName;
    private Integer classId;
    private Integer gradeId;
    private Date enrollmentDate;
    private String username;
}
