package com.example;

import com.example.annotation.Test;
import com.example.annotation.TestAnnotation;
import com.example.collection.ExampleListToMap;
import com.example.reflect.Animal;
import com.example.reflect.Bird;
import com.example.reflect.DebugInvocationHandler;
import com.example.reflect.TargetObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaGuide {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException,
            NoSuchFieldException {
        ExampleListToMap.listToMap();

        JavaGuide.reflect();

        JavaGuide.reflectAnnotations();

    }

    private static void reflectAnnotations() {
        boolean annotationPresent = Test.class.isAnnotationPresent(TestAnnotation.class);
        if (annotationPresent) {
            TestAnnotation annotations = Test.class.getAnnotation(TestAnnotation.class);
            System.out.println("annotations.msg = " + annotations.msg());
        }

        try {
            Method testMethod = Test.class.getDeclaredMethod("testMethod");
            if (testMethod != null) {
                Annotation[] annotations = testMethod.getAnnotations();
                for (Annotation annotation : annotations) {
                    System.out.println(annotation.annotationType().getName());
                    if (annotation instanceof TestAnnotation) {
                        System.out.println("((TestAnnotation) annotation).msg() = " + ((TestAnnotation) annotation)
                                .msg());
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void reflect() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        /**
         * 获取 TargetObject 类的 Class 对象并且创建 TargetObject 类实例
         */
        Class<?> targetClass = Class.forName("com.example.reflect.TargetObject");
        TargetObject targetObject = (TargetObject) targetClass.newInstance();
        /**
         * 获取 TargetObject 类中定义的所有方法
         */
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        /**
         * 获取指定方法并调用
         */
        Method publicMethod = targetClass.getDeclaredMethod("publicMethod", String.class);
        publicMethod.invoke(targetObject, "javaguide");

        /**
         * 获取指定参数，并修改参数
         */
        Field field = targetClass.getDeclaredField("value");
        field.setAccessible(true);
        field.set(targetObject, "Javaguide");

        Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(targetObject);

        Animal a = new Bird();
        DebugInvocationHandler dih = new DebugInvocationHandler(a);

        Animal proxy = (Animal) Proxy.newProxyInstance(
                a.getClass().getClassLoader(),
                a.getClass().getInterfaces(),
                dih);
        proxy.bark("foo")
                .bark("bar")
                .bark("F");
    }
}
