package com.ziheng.zhxy.mapper;

import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherPageDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2024-02-28 20:08:34
* @Entity com.ziheng.zhxy.entity.Teacher
*/
public interface TeacherMapper extends BaseMapper<Teacher> {
    // 获取所有老师的名称和id
    List<selectValueDot> getAllTeacher();

    // 根据分页指定的条件返回数据
    List<teacherPageDot> getPageconditionInfo(AdminQuery adminQuery);

    // 获取总条数
    int countTeacherSum(@Param("name") String name);

    // 根据指定id查询出用户信息
    teacherDot getByIdTeacherInfo(@Param("id") Integer id);


}




