package com.example.demo.core.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private String address;
    private List<String> favorites;
}
