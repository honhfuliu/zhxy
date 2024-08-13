package com.ziheng.zhxy.controller;

import com.ziheng.zhxy.annotations.PermissionsValidation;
import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.tClass.classDot;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.tClass.AddClassVO;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.TClassService;
import com.ziheng.zhxy.util.JsonResult;
import com.ziheng.zhxy.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("class")
@Tag(name = "班级信息管理")
public class ClassController extends BaseController{

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private TClassService classService;

    @GetMapping()
    @Operation(summary = "班级分页查询接口")
    public JsonResult<PageDOT> pageGetClass(AdminQuery adminQuery){
//        System.out.println(adminQuery.getName());
        adminQuery.setPageNo((adminQuery.getPageNo() - 1) * adminQuery.getPageSize());
        PageDOT<classDot> dot = classService.pageQueryClass(adminQuery);
        return new JsonResult<>(OK, "查询成功", dot);

    }

    @PostMapping()
    @Operation(summary = "班级添加接口")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<Void> saveClass(@RequestBody AddClassVO addClassVO) {
        String authorization = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        classService.addClass(addClassVO, tokenTuser.getUsername());
        return new JsonResult<>(OK, "添加成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除班级接口")
    @PermissionsValidation(values = "ADMIN")
    public JsonResult<Void> getByIdDelet(@PathVariable("id") Integer id){
        classService.getByIdDelete(id);
        return new JsonResult<>(OK, "删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "更具id查找具体的班级信息")
    public JsonResult<updateClassDot> getByIdClass(@PathVariable("id") Integer id) {
        updateClassDot byIdClass = classService.getByIdClass(id);
        return new JsonResult<>(OK, "查询成功", byIdClass);
    }

    @PutMapping()
    @Operation(summary = "根据id修改班级信息接口")
    @PermissionsValidation(values = {"ADMIN","TEACHER"})
    public JsonResult<Void> getByIdUpdateClass(@RequestBody AddClassVO addClassVO) {
        String authorization = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(authorization);
        classService.getByIdUpdateClass(addClassVO, tokenTuser.getUsername());
        return new JsonResult<>(OK, "更新信息成功");
    }

    @GetMapping("/add/{id}")
    @Operation(summary = "添加学生根据指定年级获取所以班级接口")
    public JsonResult<List<selectValueDot>> getGradeIdSelectInfo(@PathVariable("id") Integer id) {
        List<selectValueDot> dotList = classService.getGradeIdSelectClassIdAndName(id);
        return new JsonResult<>(OK, "查询成功", dotList);
    }


}
