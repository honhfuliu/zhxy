package com.ziheng.zhxy.domain.dot;

import lombok.Data;

import java.util.List;

@Data
public class PageDOT<T> {
    private Integer total;
    private List<T> list;
}
