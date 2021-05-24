/**
 * 父类（用于演示类继承）
 *
 * 可被继承的类要用 open 修饰（默认是 final 的，即不可继承）
 * 可被重写的属性或方法要用 open 修饰（默认是 final 的，即不可重写）
 */

package com.webabcd.androiddemo.kotlin

open class Demo8_Class1(var age: Int) {

    init {
        this.age = age
    }

    open var name = "wanglei"

    open var country = "china"

    open fun hello(): String {
        return "hello:$name, age:$age, country:$country"
    }
}