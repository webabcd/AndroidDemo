/**
 * 本例用于演示 kotlin 的字符串的常用操作（查找，替换，截取，分割，空判断，前缀判断，后缀判断，包含判断，其他操作，格式化，正则表达式等）
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*
import java.lang.Exception
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Demo4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo4)

        sample1(); // 字符串的查找、替换、截取、分割
        sample2(); // 正则表达式
        sample3(); // 字符串的空判断，前缀判断，后缀判断，包含判断，其他操作
        sample4(); // 字符串的格式化
    }

    fun sample1() {

        // 查找字符串的相关方法
        // first(), firstOrNull(), first{}, firstOrNull{}, last(), lastOrNull(), last{}, lastOrNull{}
        // find{} 相当于 firstOrNull(), findLast{} 相当于 lastOrNull()
        // indexOf(), indexLastOf()
        // indexOfFirst{} 相当于 indexOf(), indexOfLast{} 相当于 indexLastOf()

        var a: String = "webabcd";
        var b: String = "";
        try {
            // 这个会抛错的，不想抛错的话就用 firstOrNull()
            b.first();
        } catch (ex: Exception) {
            appendMessage(ex.toString()); // java.util.NoSuchElementException: Char sequence is empty.
        }
        appendMessage("${a.first()}, ${b.firstOrNull()}, ${a.firstOrNull{ it == 'e'}}"); // w, null, e

        // substring() 或 subSequence 截取字符串，两个的用法差不多
        var c: String = a.substring(1); // ebabcd
        var d: String = a.substring(2, 4); // ba
        var e: String = a.substring(IntRange(2, 4)); // bda
        // 注：2..4 和 IntRange(2, 4)
        var e2: String = a.substring(2..4); // bda
        appendMessage("$c, $d, $e, $e2");

        // split() 或 splitToSequence() 分割字符串，两个的用法差不多
        var f: List<String> = a.split('e'); // w, babcd
        var g: List<String> = a.split("ab"); // web, cd
        appendMessage("${f[0]}, ${f[1]}, ${g[0]}, ${g[1]}");

        // 字符串替换
        // 第 3 个参数传 true 用于指定忽略大小写
        var h: String = a.replace("B", "xxx", true); // wexxxaxxxcd
        // replaceFirst() - 只替换指定的第一次出现的字符串
        var i: String = a.replaceFirst("B", "xxx", true); // wexxxabcd
        // replaceBefore() - 将指定的第一次出现的字符串的左侧的所有字符串替换为指定的字符串，大小写敏感
        var j: String = a.replaceBefore("b", "xxx"); // xxxbabcd
        // replaceBeforeLast() - 将指定的最后一次出现的字符串的左侧的所有字符串替换为指定的字符串，大小写敏感
        var k: String = a.replaceBeforeLast("b", "xxx"); // xxxbcd
        // replaceAfter() - 将指定的第一次出现的字符串的右侧的所有字符串替换为指定的字符串，大小写敏感
        var l: String = a.replaceAfter("b", "xxx"); // webxxx
        // replaceAfterLast() - 将指定的最后一次出现的字符串的右侧的所有字符串替换为指定的字符串，大小写敏感
        var m: String = a.replaceAfterLast("b", "xxx"); // webabxxx
        appendMessage("$h, $i, $j, $k, $l, $m");
    }

    fun sample2() {

        // 检查字符串是否匹配指定的规则
        val r1 = "^\\d{3}$";
        var a: Boolean = Pattern.compile(r1).matcher("357").matches(); // true
        var b: Boolean = Pattern.compile(r1).matcher("abc").matches(); // false
        appendMessage("$a, $b");

        // 检索匹配指定规则的字符串
        var r2 = """\d{3}""";
        var matcher: Matcher = Pattern.compile(r2).matcher("abc 357 ijk 456 xyz");
        while (matcher.find()) {
            appendMessage(matcher.group()); // 会输出 2 条数据，一个是 357，一个是 456
        }

        // 替换匹配规则的字符串
        var r3 = "\\d+";
        appendMessage("w1eb123456abcd".replace(Regex(r3), "|")); // w|ab|abcd

        // 检索匹配指定规则的字符串，并做自定义拼接
        var r4 = "^(\\d{3}).*(\\d{3})$";
        appendMessage("123abc456defg789".replace(Regex(r4), "$1, $2")); // 123, 789

        // 根据匹配规则分割字符串
        var r5 = "[0-9]+";
        var c: List<String> = "we1ba2b3cd".split(Regex(r5));
        appendMessage("${c[0]}, ${c[1]}, ${c[2]}, ${c[3]}"); // we, ba, b, cd
    }

    fun sample3() {
        // isEmpty() - length 等于 0 则返回 true（可空字符串要先判断不为 null 之后才能调用此方法）
        // isNotEmpty() - length 大于 0 则返回 true（可空字符串要先判断不为 null 之后才能调用此方法）
        // isNullOrEmpty() - 为 null 或者 length 等于 0 则返回 true
        // isBlank() - 去掉空格后的 length 等于 0 则返回 true（可空字符串要先判断不为 null 之后才能调用此方法）
        // isNotBlank() - 去掉空格后的 length 大于 0 则返回 true（可空字符串要先判断不为 null 之后才能调用此方法）
        // isNotOrBlank() - 为 null 或者去掉空格后的 length 等于 0 则返回 true

        // count(), length, count{}
        var a: String = "webabcd"
        appendMessage("${a.count{it == 'b'}}"); // 2

        // reversed() - 反转字符串
        appendMessage("${a.reversed()}"); // dcbabew

        // startsWith(), endsWith()
        // 可以指定起始索引位置，也可以指定是否忽略大小写
        appendMessage("${a.startsWith("web")}, ${a.startsWith("ab", 3)}, ${a.startsWith("WEB", true)}"); // true, true, true

        // contains()
        // 可以指定是否忽略大小写
        appendMessage("${a.contains("a")}, ${a.contains("a", true)}"); // true, true

        // trim(), trimStart(), trimEnd()
        // 可以去掉 \r \n \t 空格之类的
        var b: String = " \n webabcd"
        appendMessage("${b.trim()}"); // webabcd

        // trimMargin() - 去掉开头的指定字符串
        var c: String = "||webabcd"
        appendMessage("${c.trimMargin("||")}"); // webabcd

        // padStart(), padEnd() - 补全字符串
        var d: String = "57";
        appendMessage("${d.padStart(6, '0')}"); // 000057
    }

    fun sample4() {
        // 可以用 plus 或 + 做字符串拼接（其实 plus() 就是 +）
        var a: String = "a".plus("b").plus("c") + "d";
        appendMessage("$a"); // abcd

        // 字符串模板（$变量名 或者 ${表达式}）
        var b: Int = 1;
        appendMessage("$b, ${ b + 1 }"); // 1, 2

        // 字符串格式化，借用 java 的类实现
        val c: Double = 1234.1;
        val f1 = DecimalFormat("0.000");
        val f2 = DecimalFormat("0.###");
        appendMessage("${f1.format(c)}, ${f2.format(c)}"); // 1234.100, 1234.1
        appendMessage(String.format(Locale.ENGLISH, "%,.2f", c)); // 1,234.10

        // 通过左右各 3 个双引号包围起来的字符串不解释转义符
        var d = """\n"$\n""";
        appendMessage("$d"); // \n"$\n
    }

    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }
}