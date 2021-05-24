/**
 * 子类（用于演示抽象类）
 */

package com.webabcd.androiddemo.kotlin

// 通过 : 继承抽象类
// 通过类似如下 override var name: String 的方式重写抽象类的抽象属性
class Demo8_Class4(override var name: String) : Demo8_Abstract() {

    init {
        this.name = name
    }

    // 重写抽象类的抽象属性
    override var country = "china"

    // 重写抽象类的抽象方法
    override fun getAge(): Int {
        return 100;
    }
}