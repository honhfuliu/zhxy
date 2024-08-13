package com.ziheng.zhxy.service.impl;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherPageDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.teacherVo.AddTeacherVo;
import com.ziheng.zhxy.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherServiceImplTest {
    @Autowired
    private TeacherService teacherService;

    @Test
    void test01() {
        List<selectValueDot> allIdAndName = teacherService.getAllIdAndName();
        System.out.println(allIdAndName);
    }

    @Test
    void test02(){
        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setPageNo(0);
        adminQuery.setPageSize(5);
        PageDOT<teacherPageDot> dot = teacherService.queryPageTeacher(adminQuery);
        System.out.println(dot.toString());
    }

    @Test
    void test03() {
        AddTeacherVo addTeacherVo = new AddTeacherVo();
        addTeacherVo.setUsername("laoshi1");
        addTeacherVo.setTName("6666");
        addTeacherVo.setEntryTime(new Date());
        addTeacherVo.setDegree("1244");
        addTeacherVo.setCourse("789");
        teacherService.addTeacher(addTeacherVo, "admin");
    }

    @Test
    void test04() {
        AddTeacherVo addTeacherVo = new AddTeacherVo();
        addTeacherVo.setTId(26);
        addTeacherVo.setUsername("teacher004");
        addTeacherVo.setTName("nima");
        addTeacherVo.setEntryTime(new Date());
        addTeacherVo.setDegree("1244");
        addTeacherVo.setCourse("789");
        teacherService.getByIdUpdateTeacher(addTeacherVo, "admin");
    }

}