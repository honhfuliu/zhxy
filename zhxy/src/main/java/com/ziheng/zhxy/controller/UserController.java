 package com.ziheng.zhxy.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ziheng.zhxy.controller.ex.*;
import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.TUserService;
import com.ziheng.zhxy.util.JsonResult;
import com.ziheng.zhxy.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("user")
@Tag(name = "用户信息接口")
public class UserController extends BaseController {
    @Autowired
    private TUserService userService;
    @Autowired
    private HttpServletRequest servletRequest;

    // 注册
    @Operation(summary = "注册")
    @PostMapping("reg")
    public JsonResult<Void> createUser(@RequestBody TUser user) {
        userService.saveUser(user);
        return new JsonResult<>(OK, "用户名注册成功！");
    }

    // 登录
    @Operation(summary = "登录")
    @PostMapping("login")
    public JsonResult<Map<String, String>> login(@RequestBody TUser user) {
        String token = userService.login(user);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        return new JsonResult<>(OK, "登录成功", stringStringHashMap);
    }

    // 更新用户信息
    @Operation(summary = "用户信息更新")
    @PutMapping("info")
    public JsonResult<Void> updateInfo(@RequestBody TUser user) {
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
//        System.out.println(tokenTuser.toString());
        userService.updateUserInfo(tokenTuser.getUid(), tokenTuser.getUsername(), user);
        return new JsonResult<>(OK, "更新成功");
    }

    // 密码修改
    @Operation(summary = "密码修改")
    @PutMapping("")
    public JsonResult<Void> updatePassword(@RequestBody JsonNode jsonNode) {
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        userService.updatePassword(tokenTuser.getUid(), jsonNode.get("oldPassword").asText(), jsonNode.get("newPassword").asText());
        return new JsonResult<>(OK, "更新成功");
    }

    // 用户上传头像
    //设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    // 设置上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
    }
    // 设置上传地址
    @Value("${upload.path}")
    String updateFilePath;


    // 头像上传
    @Operation(summary = "头像上传")
    @PostMapping("img")
    public JsonResult<String> changeAvatar(@RequestParam("file")MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        // 判断文件大小
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超过规定大小");
        }

        // 判断文件类型
        String type = file.getContentType();
        if (!AVATAR_TYPE.contains(type)) {
            throw new FileTypeException("文件类型错误");
        }
        // 文件保存位置
        String path = updateFilePath + "/upload";
        // path路径是否存在
        File dir = new File(path);
        if (!dir.exists()) { // 检测目录是否存在
            dir.mkdirs(); //创建目录
        }
        // 获取到这个文件的名称， UUID工具生成一个新的随机字符串作为名称
        String filename = file.getOriginalFilename(); // 包含名称+后缀
//        System.out.println("filename:" + filename);
        int i = filename.lastIndexOf(".");
        String suffix = filename.substring(i);
        String uploadName = UUID.randomUUID().toString().toUpperCase() + suffix;

        File dest = new File(dir, uploadName);

        // 将参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }

        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        String avatar = "upload/" + uploadName;

        //根据用户ID查询出之前保存的图片路径，之后将其删除
        String userByIdAvatar = userService.getUserByIdAvatar(tokenTuser.getUid());
        String userAvatarPath = updateFilePath + "/" + userByIdAvatar;
        Path path1 = Paths.get(userAvatarPath);
        boolean b = Files.exists(path1);
        if (b) {
            try {
                Files.delete(path1);
            } catch (IOException e) {
                throw new FileUploadException("文件上传时产生未知的异常，请于管理员联系");
            }
        }
        // 保存路径
        userService.updateUserAvatarById(tokenTuser.getUid(), tokenTuser.getUsername(), avatar);
        return new JsonResult<>(OK, "上传成功", avatar);
    }

    @GetMapping("")
    @Operation(summary = "获取用户信息")
    public JsonResult<TUser> getUserInfo() {
        String token = servletRequest.getHeader("Authorization");
        TUser tokenTuser = TokenUtil.getTokenTuser(token);
        TUser byUidInfo = userService.getByUidInfo(tokenTuser.getUid());
        return new JsonResult<>(OK, byUidInfo);
    }







}
