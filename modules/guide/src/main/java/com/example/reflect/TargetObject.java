package com.example.reflect;

public class TargetObject {
    private String value;

    public TargetObject() {
        value = "JavaGuide";
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    public void privateMethod() {
        System.out.println("value is " + value);
    }
}
