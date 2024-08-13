package com.ziheng.zhxy.service;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherPageDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.teacherVo.AddTeacherVo;
import com.ziheng.zhxy.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【teacher】的数据库操作Service
* @createDate 2024-02-28 20:08:34
*/
public interface TeacherService extends IService<Teacher> {
    /**
     * 获取所有的老师id和名称用于班级班主任添加
     * @return 老师id和名称
     */
    List<selectValueDot> getAllIdAndName();

    /**
     * 根据指定条件返回分页结果
     * @param adminQuery 分页条件
     * @return 分页数据
     */
    PageDOT<teacherPageDot> queryPageTeacher(AdminQuery adminQuery);

    /**
     * 添加老师
     * @param addTeacherVo 添加老师数据
     * @param operationUser 操作人
     */
    void addTeacher(AddTeacherVo addTeacherVo, String operationUser);

    /**
     * 根据id删除用户信息
     * @param id 删除用户的id
     */
    void getByIdDeleteTeacher(Integer id);


    /**
     * 根据id查询指定用户的信息
     * @param id 查询用户的id
     * @return 用户信息
     */
    teacherDot getByIdTeacherInfo(Integer id);

    void getByIdUpdateTeacher(AddTeacherVo teacherVo, String operationUser);


}
