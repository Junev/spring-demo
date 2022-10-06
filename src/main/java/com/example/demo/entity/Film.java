package com.example.demo.entity;

import lombok.Data;

@Data
public class Film {
    private String id;

    private String title;

    @Override
    public String toString() {
        return this.title;
    }
}