package com.ziheng.zhxy.controller;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.entity.Grade;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.GradeService;
import com.ziheng.zhxy.util.JsonResult;
import com.ziheng.zhxy.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grade")
@Tag(name = "年级管理接口")
public class GradeController extends BaseController{
    @Autowired
    private GradeService gradeService;

    @Autowired
    private HttpServletRequest servletRequest;

    @Operation(summary = "年级信息获取")
    @GetMapping()
    public JsonResult<PageDOT<Grade>> getGrade(AdminQuery adminQuery) {
        PageDOT<Grade> dot = gradeService.pageQueryGrade(adminQuery);
        return new JsonResult<>(OK, "查询成功", dot);
    }

    @Operation(summary = "年级添加接口")
    @PermissionsValidation(values = {"ADMIN"})
    @PostMapping()
    public JsonResult<Void> addGrade(@RequestBody Grade grade) {
        String authorization = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        gradeService.AddGrade(grade, tokenTuser.getUsername());
        return new JsonResult<>(OK, "添加成功");

    }

    @Operation(summary = "年级删除接口")
    @PermissionsValidation(values = {"ADMIN"})
    @DeleteMapping("/{id}")
    public JsonResult<Void> getByIdDeleteGrade(@PathVariable("id") Integer id) {
        String authorization = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        gradeService.getByIdDeleteGrade(id, tokenTuser.getUsername());
        return new JsonResult<>(OK, "删除年级成功");
    }

    @Operation(summary = "年级信息查询接口")
    @GetMapping("/{id}")
    public JsonResult<Grade> getByIdSelectGradeInfo(@PathVariable("id") Integer id) {
        Grade result = gradeService.getByIdSelectGrade(id);
        return new JsonResult<>(OK, "查询成功", result);
    }

    @Operation(summary = "年级修改接口")
    @PermissionsValidation(values = {"ADMIN"})
    @PutMapping()
    public JsonResult<Void> getByIdUpdateGradeInfo(@RequestBody Grade grade) {
        String authorization = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        gradeService.getByIdUpdateGradeInfo(grade, tokenTuser.getUsername());
        return new JsonResult<>(OK, "更新年级信息成功");
    }

    @Operation(summary = "获取id和名字用户班级添加接口")
    @GetMapping("all")
    public JsonResult<List<selectValueDot>> getAll(){
        List<selectValueDot> allGradeIdAndName = gradeService.getAllGradeIdAndName();
        return new JsonResult<>(OK, "查询成功", allGradeIdAndName);
    }





}
