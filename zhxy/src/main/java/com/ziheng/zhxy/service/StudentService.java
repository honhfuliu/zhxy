package com.ziheng.zhxy.service;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.studentDOT.studentPageDOT;
import com.ziheng.zhxy.domain.dot.studentDOT.updateStudentDOT;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.studentVo.addStudentVO;
import com.ziheng.zhxy.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【student】的数据库操作Service
* @createDate 2024-03-01 15:59:14
*/
public interface StudentService extends IService<Student> {
    PageDOT<studentPageDOT> getPageStudent(AdminQuery adminQuery);

    //新增学生
    void saveStudent(addStudentVO addStudentvo, String operationUser);

    // 删除学生
    void getByIdDelete(Integer id);

    // 获取某个学生的信息
    updateStudentDOT getByIdStudentInfo(Integer id);

    // 更新学生信息
    void updateStudentInfo(addStudentVO updateStudent,  String operationUser);
}
