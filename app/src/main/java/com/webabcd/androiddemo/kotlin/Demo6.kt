/**
 * 本例用于演示 kotlin 的函数（方法）
 * 参数可以有默认值， 传参数时可以指定参数名称，支持可变数量参数
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


    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }
}