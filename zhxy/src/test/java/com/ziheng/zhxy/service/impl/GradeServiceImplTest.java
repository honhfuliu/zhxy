package com.ziheng.zhxy.service.impl;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.Grade;
import com.ziheng.zhxy.service.GradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GradeServiceImplTest {
    @Autowired
    private GradeService service;

    @Test
//    @PermissionsValidation(values = {"admin", "AAAAA"})
    void test01() {
        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setPageSize(4);
        adminQuery.setPageNo(1);
        adminQuery.setName("一");
        PageDOT<Grade> dot = service.pageQueryGrade(adminQuery);
        System.out.println(dot);
    }

    @Test
    void test02() {
        List<selectValueDot> allGradeIdAndName = service.getAllGradeIdAndName();
        System.out.println(allGradeIdAndName);
    }

}