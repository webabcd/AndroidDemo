/**
 * 接口 2（用于演示接口）
 *
 * 接口的方法可以有自己的实现，但是属性不可以有自己的实现（需要子类实现）
 */

package com.webabcd.androiddemo.kotlin

interface Demo8_Interface2 {

    var name: String

    var age: Int

    fun hello(): String {
        return "hi:$name, age:$age"
    }
}