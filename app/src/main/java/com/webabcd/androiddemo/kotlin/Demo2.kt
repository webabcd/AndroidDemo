/**
 * 本例用于演示 kotlin 的可空类型，数据类型判断（is, !is），可空类型的相关操作符（let, ?:, !!, as?），== 和 ===
 *
 * 注：任何类型，如果声明时不加 ? 的话都是不可为 null 的，要想为 null 则声明时必须加 ?
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*
import java.lang.Exception

class Demo2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo2)

        sample1(); // 定义可空类型
        sample2(); // == 和 ===
        sample3(); // 可空类型的相关操作符（let, ?:, !!, as?）
        sample4(); // 数据类型判断（is, !is）
    }

    fun sample1() {
        var a: Int? = 1234;
        var b: Int? = null;
        var c: String? = null; // 注意：声明可空的引用类型时也是需要加 ? 的（包括 String 等引用类型，或自定义对象等）

        appendMessage("${a == 1234}, ${b == null}, ${c == null}"); // true, true, true
    }

    fun sample2() {
        // == 用于比较值是否相等
        // === 用于比较地址是否相等
        var a: Int? = 1234;
        var b: Int? = 1234;
        appendMessage("${a == b}, ${a === b}") // true, false

        // -128 到 127 之间的是例外，他们不会被装箱，所以他们的 == 和 === 是一样的
        var c: Int? = 123;
        var d: Int? = 123;
        appendMessage("${c == d}, ${c === d}") // true, true

        // 字符串比较特殊，他们的 == 和 === 是一样的
        var e: String? = "1234";
        var f: String = "1234";
        appendMessage("${e == f}, ${e === f}") // true, true
    }

    fun sample3() {
        var a: Int? = 1234;
        var b: Int? = null;

        // 对象 null 是可以 toString() 的，他的结果是字符串 null
        // ? 左侧为 null 的话就不走右边了，并返回对象 null
        appendMessage("${b.toString() == null}, ${b.toString() == "null"}, ${b?.toString() == null}"); // false, true, true

        // ?.let 左侧为 null 就不执行右边的大括号，反之则执行
        a?.let { appendMessage("aaa") }; // 会输出 aaa
        b?.let { appendMessage("bbb") }; // 不会输出 bbb

        // ?: 左侧为 null 则返回右边的值，反之则返回左边的值
        a = a ?: 123456;
        b = b ?: 123456;
        appendMessage("$a"); // 1234
        appendMessage("$b"); // 123456

        // 因为对象 null 是可以 toString() 的
        // 那如果我想 null 调用 toString() 时抛异常该怎么做呢，可以通过 !! 来实现
        var c: String? = null;
        try {
            appendMessage("${c!!.toString()}"); // 抛异常
        } catch (ex: Exception) {
            appendMessage(ex.toString()); // kotlin.KotlinNullPointerException
        }

        // as? 转换为可空类型
        var d: String = "abc";
        var e: Int? = d as? Int;
        appendMessage("$e"); // null
        // 以下两种转换方式都是会抛错的
        // appendMessage("${d as Int}"); // java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer
        // appendMessage("${d as Int?}"); // java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer
    }

    fun sample4() {
        var a: String? = null;
        // is 是否是指定的类型
        appendMessage("${a is String?}"); // true
        // !is 是否不是指定的类型
        appendMessage("${a !is String}"); // true
    }

    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }
}