package com.ziheng.zhxy.mapper;

import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.tClass.classDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.TClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_class】的数据库操作Mapper
* @createDate 2024-02-27 15:09:54
* @Entity com.ziheng.zhxy.entity.TClass
*/
public interface TClassMapper extends BaseMapper<TClass> {
    // 分页查询mapper接口
    List<classDot> pageGetClass(AdminQuery adminQuery);

    // 总条数获取
    int countClass(@Param("name") String name);

    // 根据年级id获取出这个年级的所以班级
    List<selectValueDot> getGradeIdClassIdAndName(@Param("id") Integer id);
}




