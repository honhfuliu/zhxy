package com.ziheng.zhxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherPageDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.teacherVo.AddTeacherVo;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.entity.Teacher;
import com.ziheng.zhxy.mapper.TUserMapper;
import com.ziheng.zhxy.service.TUserService;
import com.ziheng.zhxy.service.TeacherService;
import com.ziheng.zhxy.mapper.TeacherMapper;
import com.ziheng.zhxy.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2024-02-28 20:08:34
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public List<selectValueDot> getAllIdAndName() {
        List<selectValueDot> allTeacher = teacherMapper.getAllTeacher();
        return allTeacher;
    }

    // 分页条件查询
    @Override
    public PageDOT<teacherPageDot> queryPageTeacher(AdminQuery adminQuery) {
        List<teacherPageDot> pageDots = teacherMapper.getPageconditionInfo(adminQuery);
        int i = teacherMapper.countTeacherSum(adminQuery.getName());
        PageDOT<teacherPageDot> dot = new PageDOT<>();
        dot.setList(pageDots);
        dot.setTotal(i);
        return dot;
    }

    // 添加老师实现
    @Override
    public void addTeacher(AddTeacherVo addTeacherVo, String operationUser) {
        TUser resultUser = userMapper.getByUsername(addTeacherVo.getUsername());
        System.out.println(resultUser);

        if (resultUser == null) {
            throw new TeacherUsernameNotNullException("绑定的老师账号不存在, 或者是该账号已绑定老师");
        }
//        System.out.println(user);
        Teacher teacher = new Teacher();
        BeanUtil.copyProperties(addTeacherVo, teacher);
        System.out.println(teacher);
        // 补全信息
        teacher.setAccountId(resultUser.getUid());
        teacher.settId(null);
        teacher.setCreateUser(operationUser);
        teacher.setModifiedUser(operationUser);
        teacher.setCreateTime(new Date());
        teacher.setModifiedTime(new Date());
        System.out.println(teacher);

        // 将数据添加到数据库中
        boolean save = this.save(teacher);
        if (!save) {
            throw new InsertException("在插入数据时产生未知的异常，请于系统管理员进行联系");
        }

    }

    @Override
    public void getByIdDeleteTeacher(Integer id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new TeacherUsernameNotNullException("老师信息不存在");
        }
        int i = teacherMapper.deleteById(id);

        if (i != 1) {
            throw new DeleteException("删除数据时产生未知的异常，请于管理员联系");
        }
    }

    @Override
    public teacherDot getByIdTeacherInfo(Integer id) {
        teacherDot byIdTeacherInfo = teacherMapper.getByIdTeacherInfo(id);
        System.out.println(byIdTeacherInfo);
        if (byIdTeacherInfo == null) {
            throw new TeacherUsernameNotNullException("老师信息不存在");
        }
        return byIdTeacherInfo;
    }


    @Override
    public void getByIdUpdateTeacher(AddTeacherVo teacherVo, String operationUser) {
        if (teacherVo.getUsername() == null || teacherVo.getUsername().isEmpty()) {
            throw new UsernameNotNullException("名称不能为空");
        }

        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid", "username");
        queryWrapper.eq("username", teacherVo.getUsername());
        TUser user = userMapper.selectOne(queryWrapper);

//        System.out.println(user);
        Teacher teacher = teacherMapper.selectById(teacherVo.getTId());
//        System.out.println(teacher);
        TUser resultUser = new TUser();
        if (!teacher.getAccountId().equals(user.getUid())) {
            resultUser = userMapper.getByUsername(teacherVo.getUsername());
        }

        if (resultUser == null) {
            throw new TeacherUsernameNotNullException("绑定的老师账号不存在, 或者是该账号已绑定老师");
        }

        BeanUtil.copyProperties(teacherVo, teacher);
//        System.out.println(teacher);
        teacher.setAccountId(user.getUid());

        teacher.setModifiedUser(operationUser);
        teacher.setModifiedTime(new Date());

        int i = teacherMapper.updateById(teacher);
        if (i != 1) {
            throw new updateException("更新数据时产生了未知的异常，请于管理员进行联系");
        }

    }
}




