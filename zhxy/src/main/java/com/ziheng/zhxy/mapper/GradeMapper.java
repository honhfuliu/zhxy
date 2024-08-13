package com.ziheng.zhxy.mapper;

import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.entity.Grade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【grade】的数据库操作Mapper
* @createDate 2024-02-26 13:19:55
* @Entity com.ziheng.zhxy.entity.Grade
*/
public interface GradeMapper extends BaseMapper<Grade> {
    // 获取所有年级的名称和id
    List<selectValueDot> getAllGrade();
}




