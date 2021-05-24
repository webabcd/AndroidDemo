/**
 * 本例用于演示类的定义（类，属性，方法，属性的 getter 和 setter，静态属性，静态方法）
 *
 * 别管是类，还是属性，还是方法其默认都是 public 的，除了 public 外还有 private, protected, internal
 * var, val, const val, lateinit 也适用于属性，其概念参见 Demo1.kt 中的说明
 * kotlin 中的类名和包名可以与物理路径不一致（java 是要求一致的）
 */

package com.webabcd.androiddemo.kotlin

// 这里的 constructor 用于定义类的主构造函数，主构造函数只能有一个
// 对于 public 的类来说，其主构造函数可以省略 constructor
// 主构造函数的参数定义中，可以加上 var 或 val，其会自动声明同名的属性
class Demo7_Class constructor(name: String, var country: String) {

    // 声明属性
    var name: String = ""
    var age: Int = -1

    // 调用主构造函数时会执行这里
    init {
        this.name = name
    }

    // 非主构造函数，这个可以有多个
    // 后面跟 : this() 用于调用主构造函数
    constructor(name: String, age: Int, country: String) : this(name, country) {
        this.age = age
    }

    // 方法
    fun getInfo(): String {
        return "name:$name, age:$age, country:$country, weight:$weight, height:$height"
    }

    // 属性
    // 下面的代码就是 getter 和 setter 的默认实现，可以不写
    // 这里的 field 是由系统管理的，相当于 _weight
    var weight: Int = -1
        get() = field
        set(value) {
            field = value
        }

    // 属性，自定义 getter 和 setter
    var height: Int = -1
        get() = if (field < 10) 10 else field
        set(value) {
            field = value * 2
        }


    // 伴生对象
    // 用于定义静态属性和静态方法
    companion object {
        var name = "wanglei"
        fun hello(name: String): String {
            return "hello: $name"
        }
    }
}


