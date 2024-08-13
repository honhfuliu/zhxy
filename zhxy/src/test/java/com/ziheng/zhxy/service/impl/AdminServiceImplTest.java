package com.ziheng.zhxy.service.impl;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;

//    @Test
//    void test01() {
//        AdminQuery adminQuery = new AdminQuery();
//        adminQuery.setPageNo(1);
//        adminQuery.setPageSize(5);
//        PageDOT<TUser> dot = adminService.pageQueryAdmin(adminQuery);
//        System.out.println(dot);
//    }

}