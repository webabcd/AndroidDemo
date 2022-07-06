/**
 * 数据类的示例
 */

package com.webabcd.androiddemo.kotlin

// 用 data 修饰的类就是数据类
// 主构造函数中的参数，可以直接用 var 或 val 声明，其会自动生成 public 的同名属性
// 主构造函数中声明的属性会支持数据类的特性，比如解构, toString(), copy(), equals(), componentN() 等
data class Demo9_DataClass(var name: String, val age: Int) {
    // 这里声明的属性不支持数据类的特性
    var salary = 1000
}