package com.webabcd.androiddemo.kotlin;

public class HelloWorld_Java {

    public String hello(String message) {
        return "hello: " + message;
    }

    // java 调用 kotlin 的示例
    public String javaToKotlin() {
        HelloWorld_Kotlin x = new HelloWorld_Kotlin();
        return x.hello("java 调用 kotlin");
    }
}