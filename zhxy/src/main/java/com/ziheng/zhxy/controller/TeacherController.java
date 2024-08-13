package com.ziheng.zhxy.controller;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherDot;
import com.ziheng.zhxy.domain.dot.teacherDot.teacherPageDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.tClass.AddClassVO;
import com.ziheng.zhxy.domain.vo.teacherVo.AddTeacherVo;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.TeacherService;
import com.ziheng.zhxy.util.JsonResult;
import com.ziheng.zhxy.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teacher")
@Tag(name = "老师信息管理接口")
public class TeacherController extends BaseController{
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/all")
    @Operation(summary = "获取所有老师名称和id接口")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<List<selectValueDot>> getAllTeacher() {
        List<selectValueDot> allIdAndName = teacherService.getAllIdAndName();
        return new JsonResult<>(OK, "查询成功", allIdAndName);
    }

    @GetMapping()
    @Operation(summary = "分页查询接口")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<PageDOT> getQueryPageTeacher(AdminQuery adminQuery) {
        adminQuery.setPageNo((adminQuery.getPageNo() - 1) * adminQuery.getPageSize());
        PageDOT<teacherPageDot> dot = teacherService.queryPageTeacher(adminQuery);
        return new JsonResult<>(OK, "查询成功", dot);
    }

    @PostMapping()
    @Operation(summary = "老师添加接口")
    @PermissionsValidation(values = "ADMIN")
    public JsonResult<Void> addTeacher(@RequestBody AddTeacherVo addTeacherVo){
        String authorization = httpServletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        teacherService.addTeacher(addTeacherVo, tokenTuser.getUsername());
        return new JsonResult<>(OK,"添加成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除老师信息接口")
    @PermissionsValidation(values = "ADMIN")
    public JsonResult<Void> getByIdDeleteTeacher(@PathVariable("id") Integer id) {
        teacherService.getByIdDeleteTeacher(id);
        return new JsonResult<>(OK, "删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据用户id查询信息")
    public JsonResult<teacherDot> getByIdTeacherInfo(@PathVariable("id") Integer id){
        teacherDot byIdTeacherInfo = teacherService.getByIdTeacherInfo(id);
        return new JsonResult<>(OK, "查询成功", byIdTeacherInfo);
    }

    @PutMapping()
    @Operation(summary = "老师信息更新接口")
    @PermissionsValidation(values = "ADMIN")
    public JsonResult<Void> getByIdTeacherInfo(@RequestBody AddTeacherVo addTeacherVo) {
        String authorization = httpServletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        teacherService.getByIdUpdateTeacher(addTeacherVo, tokenTuser.getUsername());
        return new JsonResult<>(OK, "更新数据成功");
    }


}
