package com.ziheng.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.TUser;

public interface AdminService extends IService<TUser> {

    // 管理员信息获取功能
    PageDOT<TUser> pageQueryAdmin(AdminQuery adminQuery, String Role);

    // 管理员添加功能

    /**
     *
     * @param user  创建的用户
     * @param CreateUser 操作人
     */
    void AdminSave(TUser user, String CreateUser);

    // 根据ID 删除管理员信息

    /**
     *
     * @param uid 删除用户的id
     * @param operationUser 操作人
     */
    void getByIdDelete(Integer uid, String operationUser);

    // 根据ID查询具体的用户数据
    /**
     *
     * @param uid 用户id
     * @return
     */
    TUser SelectgetById(Integer uid);

    // 根据id 更新管理员的数据

    /**
     *
     * @param user 更新的用户信息
     * @param operationUser 操作人
     */
    void getByIdUpdateUser(TUser user, String operationUser);

}
