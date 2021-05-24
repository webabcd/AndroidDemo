/**
 * 实现类（用于演示接口）
 *
 * 接口的方法可以有自己的实现，但是属性不可以有自己的实现（需要子类实现）
 */

package com.webabcd.androiddemo.kotlin

// 通过 : 实现多个接口
// 通过类似如下 override var age: Int 的方式实现接口的属性
class Demo8_Class3(override var age: Int) : Demo8_Interface1, Demo8_Interface2 {

    init {
        this.age = age;
    }

    // 实现接口的属性
    override var name: String = ""
        get() = "wanglei"

    // 实现接口的方法
    override fun hello(): String {

        // 通过 super 调用接口的方法
        // super.hello()

        // 如果实现的多个接口有重名的方法，则可以用如下方式调用不同接口的方法
        return super<Demo8_Interface1>.hello() + " " + super<Demo8_Interface2>.hello()
    }
}