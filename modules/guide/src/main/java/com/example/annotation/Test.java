package com.example.annotation;

@TestAnnotation(msg = "hello")
public class Test {

    @TestAnnotation(msg = "I'm the testMethod.")
    public void testMethod() {
        System.out.println("Hello, I'm the testMethod.");
    }
}
