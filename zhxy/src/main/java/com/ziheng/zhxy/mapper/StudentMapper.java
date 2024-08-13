package com.ziheng.zhxy.mapper;

import com.ziheng.zhxy.domain.dot.studentDOT.studentPageDOT;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【student】的数据库操作Mapper
* @createDate 2024-03-01 15:59:14
* @Entity com.ziheng.zhxy.entity.Student
*/
public interface StudentMapper extends BaseMapper<Student> {

    // 根据分页条件查询出指定的学生信息
    List<studentPageDOT> getPageStudent(AdminQuery adminQuery);

    // 根据分页条件获取到总条数
    int getQueryPageCount(@Param("name") String name);

}




