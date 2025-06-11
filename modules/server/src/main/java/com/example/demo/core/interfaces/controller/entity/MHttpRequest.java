package com.example.demo.core.interfaces.controller.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class MHttpRequest {
    // session id
    private String i;

    // api operation
    private String t;

    // data
    private JsonNode d;

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public JsonNode getD() {
        return d;
    }

    public void setD(JsonNode d) {
        this.d = d;
    }
}
