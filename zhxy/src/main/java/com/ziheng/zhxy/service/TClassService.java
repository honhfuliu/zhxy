package com.ziheng.zhxy.service;

import com.ziheng.zhxy.domain.dot.PageDOT;
import com.ziheng.zhxy.domain.dot.selectValueDot;
import com.ziheng.zhxy.domain.dot.tClass.classDot;
import com.ziheng.zhxy.domain.dot.tClass.updateClassDot;
import com.ziheng.zhxy.domain.query.AdminQuery;
import com.ziheng.zhxy.domain.vo.tClass.AddClassVO;
import com.ziheng.zhxy.entity.TClass;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_class】的数据库操作Service
* @createDate 2024-02-27 15:09:54
*/
public interface TClassService extends IService<TClass> {

    /**
     *
     * @param adminQuery 分页查询对象类
     * @return 分页查询返回数据
     */
    PageDOT<classDot> pageQueryClass(AdminQuery adminQuery);

    /**
     *  添加班级
     * @param addClassVO 添加数据对象
     * @param operationUser 操作人
     */
    void addClass(AddClassVO addClassVO, String operationUser);

    /**
     * 更具id删除班级
     * @param id 班级id
     */
    void getByIdDelete(Integer id);

    /**
     * 根据id获取班级信息
     * @param id
     * @return
     */
    updateClassDot getByIdClass(Integer id);

    /**
     *  根据班级id更新用户信息
     * @param addClassVO 更新班级对象
     * @param operationUser 操作人
     */
    void getByIdUpdateClass(AddClassVO addClassVO, String operationUser);

    // 根据年级获取到年级所有的班级
    List<selectValueDot> getGradeIdSelectClassIdAndName(Integer id);


}
