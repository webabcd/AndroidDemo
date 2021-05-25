/**
 * 数据类的示例
 */

package com.webabcd.androiddemo.kotlin

// 构造函数中的参数，可以直接用 var 或 val 声明，其会自动生成 public 的同名属性
// class 用 data 修饰，则会自动支持解构，以及支持 copy 方法
data class Demo9_DataClass(var name: String, val age: Int)