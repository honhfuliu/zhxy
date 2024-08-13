package com.ziheng.zhxy.controller;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.AdminService;
import com.ziheng.zhxy.util.JsonResult;
import com.ziheng.zhxy.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@Tag(name = "管理员接口管理")
public class AdminController extends BaseController{
    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private AdminService adminService;

    @Operation(summary = "管理员信息展示")
    @PermissionsValidation(values = {"ADMIN"})
    @GetMapping("page")
    public JsonResult<PageDOT<TUser>> queryAdmins(AdminQuery adminQuery){
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        PageDOT<TUser> dot = adminService.pageQueryAdmin(adminQuery, tokenTuser.getRole());
        return new JsonResult<>(OK, "查询成功", dot);
    }

    @Operation(summary = "管理员注册")
    @PermissionsValidation(values = {"ADMIN"})
    @PostMapping()
    public JsonResult<Void> saveAdmin(@RequestBody TUser user) {
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        adminService.AdminSave(user, tokenTuser.getUsername());
        return new JsonResult<>(OK, "添加成功");
    }

    @Operation(summary = "删除管理员")
    @PermissionsValidation(values = {"ADMIN"})
    @DeleteMapping("/{id}")
    public JsonResult<Void> getByIdDelete(@PathVariable("id") Integer id){
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        adminService.getByIdDelete(id, tokenTuser.getUsername());
        return new JsonResult<>(OK, "删除成功");
    }

    @Operation(summary = "根据用户id查询具体的用户")
    @PermissionsValidation(values = {"ADMIN"})
    @GetMapping("/{id}")
    public JsonResult<TUser> getByIdSelectUser(@PathVariable("id") Integer id) {
        TUser user = adminService.SelectgetById(id);
        return new JsonResult<>(OK, "查询成功", user);
    }

    @Operation(summary = "根据用户id修改信息")
    @PermissionsValidation(values = {"ADMIN"})
    @PutMapping()
    public JsonResult<Void> getUserByIdUpdateInfo(@RequestBody TUser user) {
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        adminService.getByIdUpdateUser(user, tokenTuser.getUsername());
        return new JsonResult<>(OK, "用户信息修改成功");
    }


}
