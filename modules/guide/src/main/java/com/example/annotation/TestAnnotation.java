package com.example.annotation;

import java.lang.annotation.*;

/**
 * Java 自定义注解&通过反射获取类、方法、属性上的注解
 * https://blog.csdn.net/qq_28016751/article/details/83585614
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TestAnnotation {
    public String msg() default "";
}
