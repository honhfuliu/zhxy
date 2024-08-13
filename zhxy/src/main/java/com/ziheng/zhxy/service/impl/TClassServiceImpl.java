package com.ziheng.zhxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.tClass.classDot;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.tClass.AddClassVO;
import com.ziheng.zhxy.entity.TClass;
import com.ziheng.zhxy.service.TClassService;
import com.ziheng.zhxy.mapper.TClassMapper;
import com.ziheng.zhxy.service.ex.InsertException;
import com.ziheng.zhxy.service.ex.TClassNameDuplicateException;
import com.ziheng.zhxy.service.ex.TClassNoNullException;
import com.ziheng.zhxy.service.ex.updateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【t_class】的数据库操作Service实现
* @createDate 2024-02-27 15:09:54
*/
@Service
public class TClassServiceImpl extends ServiceImpl<TClassMapper, TClass>
    implements TClassService{
    @Autowired
    private TClassMapper classMapper;

    // 分页查询方法
    @Override
    public PageDOT<classDot> pageQueryClass(AdminQuery adminQuery) {
        List<classDot> classDtos = classMapper.pageGetClass(adminQuery);
        int i = classMapper.countClass(adminQuery.getName());
        PageDOT<classDot> dot = new PageDOT<>();
        dot.setTotal(i);
        dot.setList(classDtos);
        return dot;
    }

    // 添加班级实现
    @Override
    public void addClass(AddClassVO addClassVO, String operationUser) {
        TClass one = lambdaQuery()
                .eq(TClass::getClassName, addClassVO.getClassName())
                .one();
        if (one != null) {
            throw new TClassNameDuplicateException("班级名称已存在");
        }

        TClass tClass = new TClass();
        BeanUtil.copyProperties(addClassVO, tClass);
        tClass.setCreateUser(operationUser);
        tClass.setModifiedUser(operationUser);
        tClass.setCreateTime(new Date());
        tClass.setModifiedTime(new Date());

        boolean save = this.save(tClass);
        if (!save) {
            throw new InsertException("插入数据时产生未知的异常，请于管理员联系");
        }

    }

    //
    @Override
    public void getByIdDelete(Integer id) {
        TClass tClass = this.getById(id);
        if (tClass == null) {
            throw new TClassNoNullException("班级信息不存在");
        }
        int i = classMapper.deleteById(id);
        if (i != 1) {
            throw new updateException("删除时产生未知的异常，请于管理员联系");
        }
    }

    // 根据id获取班级
    @Override
    public updateClassDot getByIdClass(Integer id) {
        TClass tClass = this.getById(id);
        if (tClass == null) {
            throw new TClassNoNullException("班级信息不存在");
        }
        updateClassDot updateClassDot = new updateClassDot();
        BeanUtil.copyProperties(tClass,updateClassDot);
        return updateClassDot;
    }

    // 更新班级信息
    @Override
    public void getByIdUpdateClass(AddClassVO addClassVO, String operationUser) {
        TClass tClass = this.getById(addClassVO.getCid());
        if (tClass == null) {
            throw new TClassNoNullException("班级信息不存在");
        }
        BeanUtil.copyProperties(addClassVO, tClass);
        tClass.setModifiedTime(new Date());
        tClass.setModifiedUser(operationUser);
        System.out.println(tClass);
        int i = classMapper.updateById(tClass);
        if (i != 1) {
            throw new updateException("删除时产生未知的异常，请于管理员联系");
        }
    }

    @Override
    public List<selectValueDot> getGradeIdSelectClassIdAndName(Integer id) {
        List<selectValueDot> dots = classMapper.getGradeIdClassIdAndName(id);
        return dots;
    }
}




