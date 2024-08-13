package com.ziheng.zhxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.studentDOT.studentPageDOT;
import com.ziheng.zhxy.domain.dot.studentDOT.updateStudentDOT;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.studentVo.addStudentVO;
import com.ziheng.zhxy.entity.Grade;
import com.ziheng.zhxy.entity.Student;
import com.ziheng.zhxy.entity.TClass;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.mapper.GradeMapper;
import com.ziheng.zhxy.mapper.TClassMapper;
import com.ziheng.zhxy.mapper.TUserMapper;
import com.ziheng.zhxy.service.StudentService;
import com.ziheng.zhxy.mapper.StudentMapper;
import com.ziheng.zhxy.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【student】的数据库操作Service实现
* @createDate 2024-03-01 15:59:14
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private TClassMapper classMapper;

    @Override
    public PageDOT<studentPageDOT> getPageStudent(AdminQuery adminQuery) {
        List<studentPageDOT> pageStudent = studentMapper.getPageStudent(adminQuery);
        int count = studentMapper.getQueryPageCount(adminQuery.getName());
        PageDOT<studentPageDOT> dot = new PageDOT<>();
        dot.setTotal(count);
        dot.setList(pageStudent);
        return dot;
    }

    @Override
    public void saveStudent(addStudentVO addStudentvo, String operationUser) {
        if (addStudentvo.getSName() == null || addStudentvo.getSName().isEmpty()) {
            throw new UsernameNotNullException("学生名称不能为空");
        }
        if (addStudentvo.getUsername() == null || addStudentvo.getUsername().isEmpty()) {
            throw new UsernameNotNullException("绑定的账号不能为空");
        }

        // 判断学生传入的账号是否存在
        TUser result = userMapper.getByStuUsername(addStudentvo.getUsername());
        if (result == null) {
            throw new StudentUsernameNotNullException("绑定的学生账号不存在，或者是已被绑定");
        }

        // 判断年级是否存在
        Grade grade = gradeMapper.selectById(addStudentvo.getGradeId());
        if (grade == null){
            throw new gradeNotNullException("年级不存在");
        }

        // 判断班级是否存在年级中
        List<selectValueDot> classList = classMapper.getGradeIdClassIdAndName(grade.getGid());

        boolean b = classList.stream()
                .anyMatch(item -> addStudentvo.getClassId().equals(item.getValue()));

        if (!b) {
            throw new GradeNoClassException("年级中不存在班级，请从新选择");
        }

        // 对数据进行copy和补全
        Student student = new Student();
        BeanUtil.copyProperties(addStudentvo, student);

        student.setUserId(result.getUid());
        System.out.println(student);

        student.setCreateUser(operationUser);
        student.setCreateTime(new Date());
        student.setModifiedUser(operationUser);
        student.setModifiedTime(new Date());
        boolean save = this.save(student);

        if (!save) {
            throw new InsertException("插入数据时产生未知的异常，请于管理员联系");
        }


    }

    @Override
    public void getByIdDelete(Integer id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new StudentNoNullException("学生信息不存在");
        }
        int i = studentMapper.deleteById(student.getSid());
        if (i != 1) {
            throw new DeleteException("删除时产生未知的异常，请于管理员联系");
        }
    }

    @Override
    public updateStudentDOT getByIdStudentInfo(Integer id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new StudentNoNullException("学生信息不存在");
        }
        TUser user = userMapper.selectById(student.getUserId());
        Grade grade = gradeMapper.selectById(student.getGradeId());
        TClass tClass = classMapper.selectById(student.getClassId());

        System.out.println(student);
        updateStudentDOT dot = new updateStudentDOT();
        BeanUtil.copyProperties(student, dot);
        dot.setUsername(user.getUsername());
        dot.setGradeName(grade.getgName());
        dot.setGradeId(grade.getGid());
        dot.setClassId(tClass.getCid());
        dot.setClassName(tClass.getClassName());
        return dot;
    }

    @Override
    public void updateStudentInfo(addStudentVO updateStudent, String operationUser) {
        // 判断学生姓名和账号不能为空
        if (updateStudent.getSName() == null || updateStudent.getSName().isEmpty()) {
            throw new UsernameNotNullException("学生名称不能为空");
        }
        if (updateStudent.getUsername() == null || updateStudent.getUsername().isEmpty()) {
            throw new UsernameNotNullException("绑定的账号不能为空");
        }

        // 查询传入的账号信息
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid", "username");
        queryWrapper.eq("username", updateStudent.getUsername());
        TUser user = userMapper.selectOne(queryWrapper);

        // 查询学生信息
        Student student = studentMapper.selectById(updateStudent.getSid());
        if (student == null) {
            throw new StudentNoNullException("学生信息不存在");
        }

        TUser updateUidUser = new TUser();

        // 进行判断学生修改的信息用户名是否进行修改
        if (!student.getUserId().equals(user.getUid())) {
            updateUidUser = userMapper.getByStuUsername(updateStudent.getUsername());
        }

        // 判断修改的账号信息是否存在
        if (updateUidUser == null) {
            throw new StudentUsernameNotNullException("绑定的学生账号不存在，或者是已被绑定");
        }

        BeanUtil.copyProperties(updateStudent, student);
        student.setUserId(updateUidUser.getUid());
        student.setModifiedUser(operationUser);
        student.setModifiedTime(new Date());
//        System.out.println(student);

        // 执行更新
        int i = studentMapper.updateById(student);
        if (i != 1) {
            throw new updateException("更新数据时产生未知的异常，请于管理员联系");
        }
    }
}




