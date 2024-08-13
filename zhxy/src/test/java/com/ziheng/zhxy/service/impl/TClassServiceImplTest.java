package com.ziheng.zhxy.service.impl;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.tClass.classDot;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.tClass.AddClassVO;
import com.ziheng.zhxy.service.TClassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TClassServiceImplTest {
    @Autowired
    TClassService classService;

    @Test
    void test01() {
        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setPageSize(5);
        adminQuery.setPageNo(0);
        adminQuery.setName("四");
        PageDOT<classDot> dot = classService.pageQueryClass(adminQuery);
        System.out.println(dot);
    }

    @Test
    void test02() {
        AddClassVO addClassVO = new AddClassVO();
        addClassVO.setClassName("6669996");
        addClassVO.setGid(1);
        addClassVO.setHeadTeacherId(12);
        addClassVO.setClassTerm("8989");
        classService.addClass(addClassVO, "6666");
    }
    @Test
    void test03() {
        classService.getByIdDelete(20);
    }

    @Test
    void test04() {
        updateClassDot byIdClass = classService.getByIdClass(21);
        System.out.println(byIdClass);
    }

    @Test
    void test05(){
        List<selectValueDot> gradeIdSelectClassIdAndName = classService.getGradeIdSelectClassIdAndName(2);
        System.out.println(gradeIdSelectClassIdAndName);
    }

}