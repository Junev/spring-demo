package com.example.reflect;

public class Bird implements Animal {
    @Override
    public Bird bark(String voice) {
        System.out.println(voice);
        return this;
    }
}
