package com.ziheng.zhxy.domain.query;

public class AdminQuery extends PageQuery{
    private String name;

    public AdminQuery(String name) {
        this.name = name;
    }

    public AdminQuery() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
