/**
 * 抽象类（用于演示抽象类）
 */

package com.webabcd.androiddemo.kotlin

abstract class Demo8_Abstract {

    // 需要子类重写的属性
    abstract var name:String

    // 需要子类重写的属性
    abstract var country:String

    // 需要子类重写的方法
    abstract fun getAge(): Int

    // 有自己逻辑的方法，不需要子类重写
    fun hello(): String {
        return "hello:$name, age:${getAge()}, country:$country"
    }
}
