/**
 * 本例用于演示 kotlin 的函数（方法）
 * 参数可以有默认值， 传参数时可以指定参数名称，支持可变数量参数，支持匿名函数，支持扩展函数
 *
 * 注：
 * fun 默认是 public 的
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

class Demo6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo6)

        // 无返回值的函数
        var f1 = function1(); // function1
        var f2 = function2(); // function2
        // 返回值为 Unit 就是没有返回值的意思
        appendMessage("${f1 == Unit}, ${f2 == Unit}, ${f1 == f2}"); // true, true, true

        // 带参数带返回值的函数
        appendMessage(function3("webabcd")); // hello: webabcd

        // 参数可以有默认值
        function4("webabcd"); // name:webabcd, age:100
        function4("webabcd", 50); // name:webabcd, age:50
        // 传参数时可以指定参数名称
        // 1、指定参数名称可以使代码具有更好的可读性
        // 2、指定参数名称可以不必再管参数顺序
        function4(name = "webabcd", age = 50); // name:webabcd, age:50
        function4(name = "webabcd", country = "china"); // name:webabcd, age:100, country:china
        function4(country = "china", name = "webabcd"); // name:webabcd, age:100, country:china

        // 可变数量参数
        function5("webabcd", "p1", "p2"); // name:webabcd, params1:p1, params2:p2
        // 数组作为可变数量参数传递时，变量前要加上 *
        val array = arrayOf("p1", "p2");
        function5("webabcd", *array); // name:webabcd, params1:p1, params2:p2

        // 简单表达式的函数（无返回值的）
        function6(); // function6
        // 简单表达式的函数（有返回值的）
        appendMessage(function6(3, 7).toString()); // 21

        // 嵌套函数
        fun myNested(name: String): String {
            return "hello: $name"
        }
        appendMessage(myNested("webabcd")) // hello: webabcd

        // 匿名函数
        appendMessage(function7(3, 7).toString()); // 21

        // 扩展函数
        appendMessage(3.function8_1(7).toString()) // 21
        appendMessage(3.function8_2(7).toString()) // 21
        appendMessage(function8_2(3, 7).toString()) // 21
        // infix 扩展函数的调用方式 1
        appendMessage(3.function8_3(7).toString()) // 21
        // infix 扩展函数的调用方式 2
        appendMessage((3 function8_3 7).toString()) // 21

        // 函数作为参数
        // 通过 :: 把指定的函数当做一个参数传递到另一个函数中（如果作为参数的函数定义在其他类中的话，则写成类似 className::functionName 即可）
        appendMessage(function10("webabcd", 40, ::function9)) // function10 function9 name:webabcd, age:40
    }

    // 无返回值的函数
    // fun 默认是 public 的
    // Unit 就是没有返回值的意思，可以省略
    private fun function1(): Unit {
        appendMessage("function1");

        // 这里可以不写，或者写 return Unit; 或者写 return; 都是一样，都是没有返回值
        // return Unit;
        // return;
    }
    private fun function2() {
        appendMessage("function2");
    }

    // 带参数带返回值的函数
    fun function3(name: String): String {
        return "hello: $name"
    }

    // 参数可以有默认值
    fun function4(name: String, age: Int = 100) {
        appendMessage("name:$name, age:$age");
    }
    fun function4(name: String, age: Int = 100, country: String) {
        appendMessage("name:$name, age:$age, country:$country");
    }

    // 可变数量参数
    // 被 vararg 修饰的参数是一个固定类型的数组
    fun function5(name: String, vararg params: String) {
        appendMessage("name:$name, params1:${params[0]}, params2:${params[1]}");
    }

    // 简单表达式的函数（无返回值的）
    fun function6() = appendMessage("function6");
    // 简单表达式的函数（有返回值的）
    fun function6(a: Int, b: Int) = a * b;

    // 匿名函数（fun 后面没有函数名称）
    val function7 = fun(a: Int, b: Int): Int {
        return a * b
    }

    // 扩展函数
    // 下面的例子用于为 Int 类型扩展方法，用这种方式扩展可以类似这么调用 3.function8_1(7)
    fun Int.function8_1(other: Int): Int { return this * other }
    // 下面的例子用于为 Int 类型扩展方法，用这种方式扩展可以类似这么调用 3.function8_2(7)，也可以类似这么调用 function8_2(3, 7)
    val function8_2 = fun Int.(other: Int): Int = this * other
    // 扩展函数可以通过 infix 修饰，其除了可以类似这么调用 function8_3(3, 7) 外，还可以类似这么调用 3 function8_3 7
    infix fun Int.function8_3(other: Int): Int { return this * other }

    // 下面两个函数用于演示函数作为参数
    fun function9(name: String, age: Int): String = "function9 name:$name, age:$age"
    fun function10(myName: String, myAge: Int, method: (name: String, age: Int) -> String): String {
        return "function10 ${method(myName, myAge)}"
    }


    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }
}