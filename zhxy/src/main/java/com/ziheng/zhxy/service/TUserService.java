package com.ziheng.zhxy.service;

import com.ziheng.zhxy.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Service
* @createDate 2024-02-02 22:40:06
*/
public interface TUserService extends IService<TUser> {

    // 用户注册方法
    void saveUser(TUser user);

    // 用户登录方法
    String login(TUser user);

    // 更新用户数据方法
    void updateUserInfo(Integer uid, String username ,TUser user);

    //更新密码方法
    void updatePassword(Integer uid, String oldPassword, String newPassword);

    // 用户头像上传方法
    void updateUserAvatarById(Integer uid, String username, String avatarPath);

    // 查询用户头像地址
    String getUserByIdAvatar(Integer uid);

    // 获取用户信息
    TUser getByUidInfo(Integer uid);

}
