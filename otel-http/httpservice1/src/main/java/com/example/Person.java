package com.example;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

@Data
public class Person {

    private String name;

    @NotBlank
    private String age;


    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> System.out.println(Thread.currentThread().getId()));
        Class clz = Thread.class;
        Field field1 = null;
        try {
            field1 = clz.getDeclaredField("tid");
            field1.setAccessible(true);
            field1.set(thread1, 123);
        } catch (Exception e) {
        }

        Thread thread2 = new Thread(() -> System.out.println(Thread.currentThread().getId()));
        Class clz2 = Thread.class;
        Field field2 = null;
        try {
            field2 = clz2.getDeclaredField("tid");
            field2.setAccessible(true);
            field2.set(thread2, 123);
        } catch (Exception e) {
        }
        thread2.start();
    }
}
