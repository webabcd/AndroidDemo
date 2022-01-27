package com.webabcd.androiddemo.kotlin

class HelloWorld_Kotlin  {

    fun hello(message: String): String {
        return "hello: $message";
    }

    // kotlin 调用 java 的示例
    fun kotlinToJava(): String {
        var x = HelloWorld_Java();
        return x.hello("kotlin 调用 java")
    }
}