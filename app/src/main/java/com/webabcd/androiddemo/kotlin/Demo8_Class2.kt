/**
 * 子类（用于演示类继承）
 */

package com.webabcd.androiddemo.kotlin

// 通过 : 继承父类，调用父类的主构造函数
// 通过类似如下 override var country: String 的方式重写父类的属性
// 注：这个类也可以被继承，因为他用 open 修饰了，如果需要这个类不可以被继承，就把 open 去掉，那么就是默认 final 了，就不能被继承了
open class Demo8_Class2(age: Int, override var country: String) : Demo8_Class1(age * 2) {

    // 如下方式，通过 super 调用非主构造函数
    // constructor(country: String): super(country)

    // 属性在父类中是 val，那么是可以在子类中改用 var 修饰的
    // 属性在父类中是 var，那么是不能在子类中改用 val 修饰的

    // 重写属性
    // 注：父类中 name 是 open 的，所以如果某个类继承了这个类，那么其是可以重写此属性的，如果不想这样，那么这里就要用 final 修饰，类似这样 final override var name...
    override var name = "wanglei"

    // 重写方法
    // 注：父类中 hello() 是 open 的，所以如果某个类继承了这个类，那么其是可以重写此方法的，如果不想这样，那么这里就要用 final 修饰，类似这样 final override fun hello(): String...
    override fun hello(): String {

        // 通过 super 调用父类的属性或方法
        // super.name
        // super.hello()

        return "hi:$name, age:$age, country:$country"
    }
}
