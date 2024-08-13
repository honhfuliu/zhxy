package com.ziheng.zhxy.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.Grade;
import com.ziheng.zhxy.service.GradeService;
import com.ziheng.zhxy.mapper.GradeMapper;
import com.ziheng.zhxy.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【grade】的数据库操作Service实现
* @createDate 2024-02-26 13:19:55
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public PageDOT<Grade> pageQueryGrade(AdminQuery adminQuery) {
        Page<Grade> page = Page.of(adminQuery.getPageNo(), adminQuery.getPageSize());
        page.addOrder(OrderItem.desc("create_time"));

        Page<Grade> gradePage = lambdaQuery()
                .like(adminQuery.getName() != null, Grade::getgName, adminQuery.getName())
                .eq(Grade::getIsDelete, 0)
                .page(page);
        PageDOT<Grade> dot = new PageDOT<>();

        dot.setTotal((int)gradePage.getTotal());

        List<Grade> records = gradePage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            Grade grade = records.get(i);
            grade.setCreateUser(null);
            grade.setModifiedTime(null);
//            System.out.println(grade);
        }
        dot.setList(records);

        return dot;
    }

    @Override
    public void AddGrade(Grade grade, String operationUser) {
        if (grade.getgName() == null || grade.getgName().isEmpty()) {
            throw new gradeNameNotNullException("年级对象不能为空");
        }
        if (grade.getEndYear() == null || grade.getEndYear() == null) {
            throw  new gradeTimeNotNullException("年级创建或结束时间不能为空");
        }
        Grade result = lambdaQuery()
                .eq(Grade::getgName, grade.getgName())
                .one();

        if (result != null) {
            throw new gradeNameDuplicateException("年级重复");
        }
        grade.setCreateUser(operationUser);
        grade.setCreateTime(new Date());
        grade.setModifiedTime(new Date());
        grade.setModifiedUser(operationUser);
        int insert = gradeMapper.insert(grade);
        if (insert != 1) {
            throw new InsertException("插入数据时产生未知的异常，请于管理员联系");

        }
    }

    // 删除年级方法
    @Override
    public void getByIdDeleteGrade(Integer id, String operationUser) {
        Grade grade = gradeMapper.selectById(id);
        if (grade == null || grade.getIsDelete().equals(1)) {
            throw new gradeNotNullException("年级不存在");
        }
        Grade grade1 = new Grade();
        grade1.setGid(id);
        grade1.setIsDelete(1);
        grade1.setModifiedUser(operationUser);
        grade1.setModifiedTime(new Date());
        int i = gradeMapper.updateById(grade1);
        if (i != 1) {
            throw new updateException("删除数据时产生未知的异常，请于管理员联系");
        }
    }

    @Override
    public Grade getByIdSelectGrade(Integer id) {
        if (id == null) {
            throw new gradeIdNotNullException("年级id不能为空");
        }

        Grade grade = gradeMapper.selectById(id);
        if (grade == null || grade.getIsDelete().equals(1)) {
            throw new gradeNotNullException("年级不存在");
        }
        grade.setCreateUser(null);
        grade.setCreateTime(null);
        grade.setModifiedTime(null);
        grade.setModifiedUser(null);
        return grade;
    }

    // 根据年级id修改年级信息方法实现
    @Override
    public void getByIdUpdateGradeInfo(Grade grade, String operationUser) {
        if (grade.getGid() == null) {
            throw new gradeIdNotNullException("年级id不能为空");
        }

        Grade result = gradeMapper.selectById(grade.getGid());
        if (result == null || result.getIsDelete().equals(1)) {
            throw new gradeNotNullException("年级不存在");
        }
        if (grade.getEndYear() == null || grade.getEndYear() == null) {
            throw  new gradeTimeNotNullException("年级创建或结束时间不能为空");
        }

        grade.setModifiedUser(operationUser);
        grade.setModifiedTime(new Date());

        int i = gradeMapper.updateById(grade);

        if (i != 1) {
            throw new updateException("修改年级的时候出现了未知的异常，请于管理员联系");
        }
    }

    // 年级信息（id 和名称获取）获取
    @Override
    public List<selectValueDot> getAllGradeIdAndName() {
        return gradeMapper.getAllGrade();
    }
}




