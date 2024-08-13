package com.ziheng.zhxy.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.TUser;

import com.ziheng.zhxy.mapper.TUserMapper;
import com.ziheng.zhxy.service.AdminService;
import com.ziheng.zhxy.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl extends ServiceImpl<TUserMapper, TUser> implements AdminService{

    @Autowired
    private TUserMapper userMapper;

    // 管理员信息获取方法实现
    @Override
    public PageDOT<TUser> pageQueryAdmin(AdminQuery adminQuery, String Role) {
        if (!Role.equals("ADMIN")) {
            throw  new NoPermissionException("你没有权限访问");
        }
        String name = adminQuery.getName();
//        System.out.println(name);
        Page<TUser> page = Page.of(adminQuery.getPageNo(), adminQuery.getPageSize());
        // 按照更新时间降序排序
        page.addOrder(OrderItem.desc("create_time"));

        Page<TUser> page1 = lambdaQuery()
                .like(name != null, TUser::getUsername, name)
                .eq(TUser::getRole, "ADMIN")
                .eq(TUser::getIsDelete, 0)
                .page(page);

        PageDOT<TUser> dot = new PageDOT<>();

        dot.setTotal((int) page1.getTotal());

        List<TUser> users = page1.getRecords();
        for (int i = 0; i < users.size(); i++) {
            TUser tUser = users.get(i);
            tUser.setPassword(null);
            tUser.setSalt(null);
            tUser.setAvatar(null);
            tUser.setCreateUser(null);
            tUser.setModifiedTime(null);
            tUser.setModifiedUser(null);
        }
        dot.setList(users);
        return dot;
    }

    // 管理员添加功能实现
    @Override
    public void AdminSave(TUser user, String CreateUser) {
        if ((user.getUsername() != null && user.getUsername().isEmpty())) {
            throw  new UsernameNotNullException("用户名不能为空");
        }
        TUser result = lambdaQuery()
                .eq(TUser::getUsername, user.getUsername())
                .one();

        if (result != null) {
            throw  new UsernameDuplicateException("用户名已存在");
        }

        user.setRole("ADMIN");
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 之后补全日志信息
        user.setModifiedUser(CreateUser);
        user.setCreateUser(CreateUser);
        user.setModifiedTime(new Date());
        user.setCreateTime(new Date());

        // 插入数据库
        boolean save = this.save(user);
        if (!save) {
            throw  new InsertException("插入数据时产生未知的异常，请于管理员联系");
        }


    }

    // 删除用户实现方法
    @Override
    public void getByIdDelete(Integer uid, String operationUser) {
        TUser result = userMapper.selectById(uid);
        if (result == null || result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        TUser user = new TUser();
        user.setUid(uid);
        user.setIsDelete(1);
        user.setModifiedUser(operationUser);
        user.setModifiedTime(new Date());
        int i = userMapper.updateById(user);
        if (i != 1) {
            throw new updateException("修改数据时产生未知的一样，请于管理员联系");
        }
    }

    // 查询具体的用户信息
    @Override
    public TUser SelectgetById(Integer uid) {
        TUser result = userMapper.selectById(uid);
        if (result == null || result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        result.setPassword(null);
        result.setSalt(null);
        result.setAvatar(null);
        result.setCreateUser(null);
        result.setModifiedTime(null);
        result.setModifiedUser(null);
        return result;
    }

    // 修改用户信息方法实现
    @Override
    public void getByIdUpdateUser(TUser user, String operationUser) {
        TUser result = userMapper.selectById(user.getUid());
        if (result == null || result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        String md5Password = getMd5Password(user.getPassword(), result.getSalt());
        user.setPassword(md5Password);
        user.setModifiedTime(new Date());
        user.setModifiedUser(operationUser);
        int i = userMapper.updateById(user);
        if (i != 1) {
            throw new updateException("修改数据时产生未知的一样，请于管理员联系");
        }
    }

    /*md5算法加密处理*/
    private String getMd5Password(String password, String salt) {
        // md5加密算法方法调用（进行三次加密）
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
