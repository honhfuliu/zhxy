package com.ziheng.zhxy.controller;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.studentDOT.studentPageDOT;
import com.ziheng.zhxy.domain.dot.studentDOT.updateStudentDOT;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.studentVo.addStudentVO;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.mapper.StudentMapper;
import com.ziheng.zhxy.service.StudentService;
import com.ziheng.zhxy.util.JsonResult;
import com.ziheng.zhxy.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stu")
@Tag(name = "学生信息接口管理")
public class StudentController extends BaseController{
    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping()
    @Operation(summary = "根据分页条件获取学生信息接口")
    public JsonResult<PageDOT<studentPageDOT>> getStudentInfo(AdminQuery adminQuery){
        adminQuery.setPageNo((adminQuery.getPageNo() - 1) * adminQuery.getPageSize());
        PageDOT<studentPageDOT> pageStudent = studentService.getPageStudent(adminQuery);
        return new JsonResult<>(OK, "查询成功", pageStudent);
    }

    @PostMapping()
    @Operation(summary = "新增学生接口")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<Void> saveStudent(@RequestBody addStudentVO addStudent) {
        String authorization = httpServletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        studentService.saveStudent(addStudent, tokenTuser.getUsername());
        return new JsonResult<>(OK, "添加成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除学生信息接口")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<Void> getByIdDeleteStudent(@PathVariable("id") Integer id) {
        studentService.getByIdDelete(id);
        return new JsonResult<>(OK, "删除成功");
    }

    @GetMapping("/{id}")
    public JsonResult<updateStudentDOT> getByIdStudent(@PathVariable("id") Integer id) {
        updateStudentDOT dot = studentService.getByIdStudentInfo(id);
        return new JsonResult<>(OK, "查询成功", dot);
    }

    @PutMapping()
    @Operation(summary = "更新学生信息")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<Void> getByIdUpdateStudentInfo(@RequestBody addStudentVO updateStudent){
        String authorization = httpServletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        studentService.updateStudentInfo(updateStudent, tokenTuser.getUsername());
        return new JsonResult<>(OK, "更新成功");
    }
}
