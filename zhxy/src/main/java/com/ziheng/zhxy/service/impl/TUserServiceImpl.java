package com.ziheng.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.exceptions.PasswordExpiredException;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.TUserService;
import com.ziheng.zhxy.mapper.TUserMapper;
import com.ziheng.zhxy.service.ex.*;
import com.ziheng.zhxy.util.TokenUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2024-02-02 22:40:06
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

    private static List<String> role;
    static {
        // 初始化 ArrayList
        role = new ArrayList<>(Arrays.asList("ADMIN", "TEACHER", "STUDENT"));
    }

    @Autowired
    private TUserMapper userMapper;

    // 注册方法
    @Override
    public void saveUser(TUser user) {
        if (!role.contains(user.getRole())) {
            throw new UnauthorizedAccessException("非法访问数据异常");
        }

        TUser user1 = this.lambdaQuery()
                .eq(user.getUsername() != null, TUser::getUsername, user.getUsername())
                .one();
        if (user1 != null) {
            throw new UsernameDuplicateException("用户名被占用");
        }

        String ordPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMd5Password(ordPassword, salt);
        user.setPassword(md5Password);

        // 补全日志
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(new Date());
        user.setCreateUser(user.getUsername());
        user.setCreateTime(new Date());

        // 插入到数据库中

        boolean result = this.save(user);
        if (!result) {
            throw new InsertException("插入数据时产生异常，请于管理员联系");
        }


    }

    // 登录方法
    @Override
    public String login(TUser user) {
        if (!role.contains(user.getRole())) {
            throw new UnauthorizedAccessException("非法访问数据异常");
        }
        TUser result = lambdaQuery()
                .eq(user.getUsername() != null, TUser::getUsername, user.getUsername())
                .one();
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        if (!result.getRole().equals(user.getRole())) {
            throw new UnauthorizedAccessException("非法访问数据异常");
        }


        String md5Password = getMd5Password(user.getPassword(), result.getSalt());

        if (!result.getPassword().equals(md5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }

        user.setUid(result.getUid());
        // 生成token
        TokenUtil tokenUtil = new TokenUtil();
        return tokenUtil.createToken(user);


    }

    // 更新用户数据方法
    @Override
    public void updateUserInfo(Integer uid, String username ,TUser user) {
        System.out.println(uid);
        TUser result = userMapper.selectById(uid);

        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        boolean b = this.updateById(user);
        if (!b) {
            throw new updateException("更新数据时产生未知异常，请于管理员联系");
        }

    }

    // 修改密码方法
    @Override
    public void updatePassword(Integer uid, String oldPassword, String newPassword) {
        TUser result = userMapper.selectById(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        String md5Password = getMd5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(md5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        TUser user = new TUser();
        String md5Password1 = getMd5Password(newPassword, result.getSalt());
        user.setUid(uid);
        user.setPassword(md5Password1);
        user.setModifiedUser(result.getUsername());
        user.setModifiedTime(new Date());
        boolean b = this.updateById(user);
        if (!b) {
            throw new updateException("更新数据时产生未知的异常，请于管理员联系");
        }

    }

    // 用户头像方法实现
    @Override
    public void updateUserAvatarById(Integer uid, String username, String avatarPath) {
//        TUser result = userMapper.selectById(uid);
//        if (result == null || result.getIsDelete() == 1) {
//            throw new UserNotFoundException("用户数据不存在");
//        }
        TUser user = new TUser();
        user.setUid(uid);
        user.setAvatar(avatarPath);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        boolean b = this.updateById(user);
        if (!b) {
            throw new updateException("更新数据时产生未知的异常，请于管理员联系");
        }
    }

    // 更具用户的uid查询用户头像保存地址
    @Override
    public String getUserByIdAvatar(Integer uid) {
        TUser result = userMapper.selectById(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        return result.getAvatar();
    }

    // 获取用户信息方法
    @Override
    public TUser getByUidInfo(Integer uid) {
        TUser result = userMapper.selectById(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        result.setPassword(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        result.setCreateTime(null);
        result.setCreateUser(null);
        result.setIsDelete(null);
        result.setRole(null);

        return result;
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




