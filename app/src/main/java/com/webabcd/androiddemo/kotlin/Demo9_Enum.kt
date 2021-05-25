/**
 * 枚举的示例
 */

package com.webabcd.androiddemo.kotlin

enum class Demo9_Enum1 {
    // 枚举常量
    OK,
    ERROR
}

// 带构造函数的枚举
enum class Demo9_Enum2 (var status: Int) {
    // 枚举常量（使用枚举构造函数）
    OK(100),
    ERROR(200); // 这里要有分号，用于分隔枚举常量和方法

    // 枚举中的方法
    fun getMessage() = "message:$status"
}