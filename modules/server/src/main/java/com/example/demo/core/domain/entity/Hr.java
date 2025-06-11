package com.example.demo.core.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hr implements Serializable{
    private Integer id;

    private String name;

    private String phone;

    private String telephone;

    private String address;

    private Integer enabled;

    private String username;

    private String password;

    private String userface;

    private String remark;

}
