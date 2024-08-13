package com.ziheng.zhxy.service;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【grade】的数据库操作Service
* @createDate 2024-02-26 13:19:55
*/
public interface GradeService extends IService<Grade> {
    // 班级信息获取

    /**
     *
     * @param adminQuery 分页查询对象
     * @return 查询获取到的对象
     */
    PageDOT<Grade> pageQueryGrade(AdminQuery adminQuery);

    /**
     * 添加年级
     * @param grade 年级对象
     * @param operationUser 操作人
     */
    void AddGrade(Grade grade, String operationUser);

    /**
     * 删除年级
     * @param id 年级id
     * @param operationUser 操作人
     */
    void getByIdDeleteGrade(Integer id, String operationUser);

    /**
     * 根据id查询年级信息
     * @param id 年级id
     * @return 年级信息
     */
    Grade getByIdSelectGrade(Integer id);

    /**
     * 根据id 修改年级信息
     * @param grade  修改年级信息对象
     * @param operationUser 操作人
     */
    void getByIdUpdateGradeInfo(Grade grade, String operationUser);

    /**
     * 获取所有的年级信息
     * @return 年级id 和 年级名称
     */
    List<selectValueDot> getAllGradeIdAndName();

}
