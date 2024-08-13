package com.ziheng.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.TUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TUserServiceImplTest {
    @Autowired
    private TUserService userService;

    @Test
    void save_user_test01() {
        TUser tUser = new TUser();
        tUser.setUsername("rencai");
        tUser.setPassword("123456");
        tUser.setRole("ADMIN");

        userService.saveUser(tUser);
    }

    @Test
    void login_test02() {
        TUser tUser = new TUser();
        tUser.setUsername("rencai");
        tUser.setPassword("123456");
        tUser.setRole("ADMIN1");
        String login = userService.login(tUser);
        System.out.println(login);

    }

    @Test
    void update_info() {
        TUser tUser = new TUser();
        tUser.setPhone("18869700309");
        tUser.setEmail("3120@qq.com");
        tUser.setGender(0);
        userService.updateUserInfo(5,"rencai", tUser);
    }

    @Test
    void update_password() {
        userService.updatePassword(1, "123456", "654321");
    }


    @Test
    void query() {
//        AdminQuery adminQuery = new AdminQuery();
//        adminQuery.setUsername("ren");
//        adminQuery.setPage(1);
//        adminQuery.setSize(5);
//        Page<TUser> page = Page.of(adminQuery);
//        Page<TUser> page1 = userService.page(page);
//        List<TUser> users = page1.getRecords();
//        System.out.println(users);
    }
}