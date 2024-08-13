package com.ziheng.zhxy.service.impl;

import com.ziheng.zhxy.domain.dot.studentDOT.updateStudentDOT;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.vo.studentVo.addStudentVO;
import com.ziheng.zhxy.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceImplTest {
    @Autowired
    private StudentService studentService;

    @Test
    void test01(){
        addStudentVO add = new addStudentVO();
        add.setUsername("zhangsan1");
        add.setSName("老六");
        add.setGradeId(1);
        add.setClassId(11);
        studentService.saveStudent(add, "admin");
    }

    @Test
    void test02() {
        updateStudentDOT byIdStudentInfo = studentService.getByIdStudentInfo(23);
        System.out.println(byIdStudentInfo);
    }

    @Test
    void test03() {
        addStudentVO addStudentVO = new addStudentVO();
        addStudentVO.setSid(23);
        addStudentVO.setUsername("rencai2");
        addStudentVO.setSName("66666");
        addStudentVO.setGradeId(10);
        addStudentVO.setClassId(10);
        studentService.updateStudentInfo(addStudentVO, "admin");
    }

}