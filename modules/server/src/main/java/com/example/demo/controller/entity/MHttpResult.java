package com.example.demo.controller.entity;

public class MHttpResult {
    // 操作成功
    private Integer s;

    // 响应的数据
    private Object d;

    private MHttpResult(Integer s, Object d) {
        this.s = s;
        this.d = d;
    }

    public static <T extends Object> MHttpResult success(T d) {
        return new MHttpResult(0, d);
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    public Object getD() {
        return d;
    }

    public void setD(Object d) {
        this.d = d;
    }
}
