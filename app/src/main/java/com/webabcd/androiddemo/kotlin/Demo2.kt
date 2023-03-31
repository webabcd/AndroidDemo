/**
 * 本例用于演示 kotlin 的可空类型，数据类型判断（is, !is），类型转换，可空类型的相关操作符（let, ?:, ?, !!, as?），== 和 ===
 *
 * 注：任何类型，如果声明时不加 ? 的话都是不可为 null 的，要想为 null 则声明时必须加 ?
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.databinding.ActivityKotlinDemo2Binding
import java.lang.Exception

class Demo2 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinDemo2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinDemo2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sample1(); // 定义可空类型
        sample2(); // == 和 ===
        sample3(); // 可空类型的相关操作符（let, ?:, ?, !!, as?）
        sample4(); // 数据类型判断（is, !is）和类型转换
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

        // 虽然对象 null 是不可以 toString() 的，但是 toString() 方法里会有判断，如果是 null 则返回字符串 null
        // ? 左侧为 null 的话就不走右边了，并返回对象 null
        appendMessage("${b.toString() == null}, ${b.toString() == "null"}, ${b?.toString() == null}"); // false, true, true

        // ?.let 左侧为 null 就不执行右边的大括号，反之则执行
        a?.let { appendMessage("aaa") }; // 会输出 aaa
        b?.let { appendMessage("bbb") }; // 不会输出 bbb
        // ?.let 有一个参数，其代表 ?.let 左侧对象的值
        a?.let { appendMessage("$it") }     // 1234
        a?.let { p -> appendMessage("$p") } // 1234

        // ?: 左侧为 null 则返回右边的值，反之则返回左边的值
        a = a ?: 123456;
        b = b ?: 123456;
        appendMessage("$a"); // 1234
        appendMessage("$b"); // 123456

        // 虽然对象 null 是不可以 toString() 的，但是 toString() 方法里会有判断，如果是 null 则返回字符串 null
        // 通过 !! 可以对一个可空对象强行取值，如果是 null 则会抛出异常
        var c: String? = null;
        try {
            appendMessage("${c.toString()}");   // null
            appendMessage("${a!!}");            // 1234
            appendMessage("${c!!}");            // 抛异常
        } catch (ex: Exception) {
            appendMessage(ex.toString());       // kotlin.KotlinNullPointerException
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

        // 类型转换的方法（包括 toString(), toByte(), toShort(), toInt(), toLong(), toFloat(), toDouble(), toChar() 等）
        var b = "123".toInt()
        // 有继承关系的类型的转换可以用 as 或 as?
        var c = 123L as Number
        var d = c as Long
        // 隐式类型转换
        var e = "abc" + 123
        appendMessage("$b, $c, $d, $e") // 123, 123, 123, abc123

        // 对于没有继承关系的类型，如果你用 as 或 as? 转换的话
        // "123" as Int     // 这个会报错 java.lang.String cannot be cast to java.lang.Integer
        // "123" as Int?    // 这个会报错 java.lang.String cannot be cast to java.lang.Integer
        // "123" as? Int    // 这个转换结果为 null
    }

    fun appendMessage(message: String) {
        mBinding.textView1.append(message);
        mBinding.textView1.append("\n");
    }
}