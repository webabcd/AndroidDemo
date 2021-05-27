/**
 * 本例用于演示泛型（泛型类，泛型接口，泛型方法，泛型约束，型变）
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

class Demo11 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo11)

        sample1() // 泛型类，泛型接口，泛型方法
        sample2() // 泛型约束
        sample3() // 型变
    }

    fun sample1() {
        // 调用泛型类
        var a: Class1<String> = Class1<String>("webabcd") // 写全代码的
        var b = Class1("webabcd") // 编译器自己做类型推断的
        appendMessage("${a.value}, ${b.value}") // webabcd, webabcd

        // 调用泛型方法
        var c: String = function1<String>("webabcd") // 写全代码的
        var d = function1("webabcd") // 编译器自己做类型推断的
        appendMessage("$c, $d") // webabcd, webabcd
    }
    // 泛型类（泛型接口也类似，我就不写了）
    fun<T> function1(value: T) : T {
        return value;
    }
    // 泛型方法
    class Class1<T>(t: T) {
        var value = t
    }



    fun sample2() {
        var a = Class2("webabcd");
        var b = Class3("wanglei");

        appendMessage(function2(a)) // a 是 Class2 类型，符合约束条件
        appendMessage(function2(b)) // b 是 Class3 类型，其继承自 Class2 所以符合约束条件

        appendMessage(function3(b)) // b 是 Class3 类型，其同时继承自 Class2 和 Interface1 所以符合约束条件
        // appendMessage(function3(a)) // a 是 Class2 类型，其没有继承自 Interface1 所以不符合约束条件，会在编译时报错
    }
    // 泛型只有一个约束条件的写法
    // 下例 T 必须是 Class2 或是 Class 2 的子类
    fun<T: Class2> function2(param: T): String {
        return param.name
    }
    // 泛型有一个约束条件或多个约束条件的写法
    // 下例 T 必须同时继承自 Class2 和 Interface1
    fun<T> function3(param: T): String  where T: Interface1, T: Class2  {
        return param.name
    }
    interface Interface1 {
        var name: String
    }
    open class Class2(var name: String) {

    }
    class Class3(name: String) : Class2(name), Interface1 {

    }



    // 型变的示例
    fun sample3() {

        // Class5 是 Class4 的子类，所以下面这个协变是正常的
        var x: Class4 = Class5()


        var a = Class6(Class5())
        // 虽然 Class5 是 Class4 的子类，但是 Class6<Class5> 却不是 Class6<Class4> 的子类，所以下面这句协变会编译报错
        // var b: Class6<Class4> = a


        var c = Class7(Class5())
        // 看一下 Class7 的 out 修饰符，有了它下面这句协变就正常了（没有 out 则编译报错）
        var d: Class7<Class4> = c


        var e = Class8(Class4())
        // 看一下 Class8 的 in 修饰符，有了它下面这句逆变就正常了（没有 in 则编译报错）
        var f: Class8<Class5> = e


        var g = Class6(Class5())
        // 如果不想在类的泛型定义中加 out 修饰符，又想协变怎么办呢
        // 那就像下面这样，在类型声明处加上 out 修饰符
        var h: Class6<out Class4> = g


        var i = Class6(Class5())
        // 如果你也不确定基类是啥，怎么办
        // 当然可以用 out Any? 是没问题的，另外也可以用 *
        // 但是这样协变后，你就不能把泛型参数传入对象的任何方法了（即泛型参数可读不可写），会编译报错（因为这样协变后就不知道泛型的参数类型了）
        var j: Class6<out Any?> = i
        var k: Class6<*> = i
    }
    class Class6<T>(a: T) { }
    class Class7<out T>(a: T) { }
    class Class8<in T>(a: T) { }
    open class Class4() { }
    class Class5() : Class4() { }



    fun appendMessage(message: String) {
        textView1.append(message)
        textView1.append("\n")
    }
}
