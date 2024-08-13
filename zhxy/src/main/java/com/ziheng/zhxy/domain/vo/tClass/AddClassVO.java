package com.ziheng.zhxy.domain.vo.tClass;

import lombok.Data;

// 班级添加数据接收类
@Data
public class AddClassVO {
    private Integer cid;
    private Integer gid;
    private String className;
    private Integer headTeacherId;
    private String classTerm;

}
