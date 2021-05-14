/**
 * 本例用于演示 kotlin 的基本数据类型，位操作，变量和常量，注释可嵌套，lateinit，by lazy
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

// 常量（用 const val 修饰）,在顶层声明的示例
const val MY_CONST1: String = "MY_CONST1";

class Demo1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo1)

        sample1(); // 基本数据类型
        sample2(); // 数组
        sample3(); // 位操作
        sample4(); // 变量和常量
        sample5(); // 注释可嵌套
        sample6(); // lateinit
        sample7(); // by lazy
    }

    fun sample1() {
        // 声明变量时，自行推导数据类型，当然必须要初始化
        var a = 1;
        var b = true;
        var c = "abc";
        var d = 'x';

        // L 长整形，f 浮点型，0x 十六进制，0b 二进制
        var e = 1L;
        var f = 1f;
        var g = 0xFFFF; // 65535
        var h = 0b1111111111111111;  // 65535
        var i = 65535;  // 65535

        // 可以通过 _ 连接数字，以便阅读
        var j = 0xFF_FF;  // 65535
        var k = 0b1111_1111_1111_1111;  // 65535
        var l = 65_535;  // 65535

        // 声明变量时，指定数据类型
        // 基本数据类型有：Byte, Short, Int, Long, Float, Double, Boolean, Char, String, Array
        var m: String;
        m = "xyz";

        // 数据类型隐式转换
        var n: String = "n" + 1; // n1
        // 数据类型强制转换
        var o: String = "o" + 1.toString(); // o1

        // && || ! 的用法和 java 中是一样的
        var p = true && true;
        var q = true || false;
        var r = !false;

        // 字符转 ASCII 码
        var s = 'a'.toInt(); // 97
        // ASCII 码转字符 // a
        var t = 97.toChar();
        // Unicode 码转字符串
        var u = "\u738B"; // 王

        // 遍历字符串
        var v = "";
        for (item in "webabcd") {
            v += item;
        } // webabcd
        // 获取指定字符串的指定索引位置的字符
        var w = v[3]; // a
        // 转义符符
        // \t, \r, \n, \b, \\, \', \", \$
        // 通过左右各 3 个双引号包围起来的字符串不解释转义符
        var x = """\n"$\n"""; // \n"$\n"
        // 去掉指定的前缀
        var y = x.trimMargin("\\n"); // "$\n

        // 字符串模板（$变量名 ${表达式}）
        appendMessage("$a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $v, $w, $x, $y, ${ 1 + 1 }");
    }

    fun sample2() {
        // 任意类型数据的数组
        var a = arrayOf(1, true, "abc"); // 1, true, "abc"
        // 可空数据的数组（传入的参数为数组长度）
        var b = arrayOfNulls<Int>(3);
        b[0] = 1;
        b[1] = null;
        b[2] = 3; // 1, null, 3
        // 指定类型数据的数组
        var c: Array<String> = arrayOf("a", "b", "c"); // "a", "b", "c"
        var d: Array<Int> = arrayOf(1, 2, 3); // 1, 2, 3（每个元素的类型相当于 java 的 Integer，是引用类型）
        // 值类型数据数组 ByteArray, ShortArray, IntArray, LongArray, BooleanArray, CharArray, FloatArray, DoubleArray
        var e: IntArray = intArrayOf(1, 2, 3); // 1, 2, 3（每个元素的类型相当于 java 的 int，是值类型）

        // 第 1 个参数：数组长度
        // 第 2 个参数：一个表达式，根据元素的索引位置指定元素值
        var f = Array(5, { index -> (index * 2).toString() }); // "0", "2", "4", "6", "8"

        appendMessage("${a[1]}, ${b[1]}, ${c[1]}, ${d[1]}, ${e[1]}, ${f[1]}");
    }

    fun sample3() {
        // 位操作

        // and 相当于 java 的 &
        // or 相当于 java 的 |
        // inv 相当于 java 的 ~
        // xor 相当于 java 的 ^
        // shl 相当于 java 的 <<
        // shr 相当于 java 的 >>
        // ushr 相当于 java 的 >>>

        var a: Int = 0b0101 or 0b0011;
        appendMessage("$a"); // 结果是 7（二进制表示就是 0b0111）
    }

    fun sample4() {
        // 变量（用 var 修饰）
        var a = 1;
        // 不可变的变量（用 val 修饰）
        val b = 1;

        // 常量（用 const val 修饰），可以在如下 3 种地方声明
        // 1、在顶层声明，参见本文件的 MY_CONST1
        // 2. 在 object 修饰的类中声明，参见本文件的 MY_CONST2
        // 3. 在伴生对象中声明，参见本文件的 MY_CONST3
        // 注：不可变变量和常量的区别：不可变变量是运行时初始化的，常量是编译时初始化的

        appendMessage("$a, $b, $MY_CONST1, ${MyObject.MY_CONST2}, ${MyClass.MY_CONST3}");
    }

    fun sample5() {
        // 支持注释嵌套了
        /*
        /*

         */
         */
    }

    // lateinit 延迟初始化
    // 在类中声明变量的话，是必须要初始化的
    // 如果不想初始化则可以用 lateinit 修饰（但是 Int, Float, Boolean 等值类型的变量是不支持的）
    lateinit var var_lateinit: String
    fun sample6() {
        var_lateinit = "abc"
        appendMessage("$var_lateinit");
    }

    // by lazy 延迟初始化（第一次调用时初始化）
    val var_lazy: String by lazy {
        appendMessage("xxxxxxxxxx"); // 只有第一次调用时会走到这里
        "var_lazy"
    }
    fun sample7() {
        appendMessage("$var_lazy");
        appendMessage("$var_lazy");
        appendMessage("$var_lazy");
        // 连续调用 3 次 var_lazy 你会得到如下打印
        // xxxxxxxxxx
        // var_lazy
        // var_lazy
        // var_lazy
    }


    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }

    object MyObject{
        // 常量（用 const val 修饰），在 object 修饰的类中声明的示例
        const val MY_CONST2 = "MY_CONST2"
    }

    class MyClass{
        companion object {
            // 常量（用 const val 修饰），在伴生对象中声明的示例
            const val MY_CONST3 = "MY_CONST3"
        }
    }
}

