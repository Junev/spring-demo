package com.example.demo.core.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String nameZh;
}
