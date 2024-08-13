package com.ziheng.zhxy.mapper;

import com.ziheng.zhxy.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2024-02-02 22:40:06
* @Entity com.ziheng.zhxy.entity.TUser
*/

public interface TUserMapper extends BaseMapper<TUser> {
    // 根据传入的用户名查询该用户是否绑定教师
    TUser getByUsername(@Param("name") String name);

    // 根据传入的用户名查询该用户是否绑定学生
    TUser getByStuUsername(@Param("name") String name);

}




