/**
 * 本例用于演示 kotlin 的数组和集合的常用操作
 *
 * 1、简单的数组操作
 * 2、不可变集合 List, Set, Map；可变集合 MutableList, MutableSet, MutableMap
 * 3、查找某个位置的元素，遍历元素，查找符合指定条件的元素，查找指定范围的元素
 * 4、元素排重，排序，统计，判断是否包含指定元素
 * 5、映射，分组，连接，合并，分拆，分区
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

class Demo5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo5)

        sample1(); // 数组
        sample2(); // 集合 List, Set, Map
        sample3(); // 查找某个位置的元素，遍历元素
        sample4(); // 查找符合指定条件的元素，查找指定范围的元素，元素排重
        sample5(); // 排序
        sample6(); // 统计，判断是否包含指定元素
        sample7(); // 映射，分组
        sample8(); // 连接，合并，分拆，分区
    }

    fun sample1() {
        val a = arrayOf(1, 2, 3, 4, 5);

        // component1(), component2(), component3(), component4(), component5() - 获取第 n 个元素
        // in - 用于判断指定的元素是否在数组中
        // !in - 用于判断指定的元素是否不在数组中
        appendMessage("${a[0]}, ${a.component1()}, ${ 1 in a}, ${1 !in a}"); // 1, 1, true, false
    }

    fun sample2() {
        // 实例化 List 集合
        val a: Array<Any> = arrayOf("1", "2", 3, 4, 5);
        val b: List<Any> = listOf(a);
        val c: List<Any> = listOf("1", "2", 3, 4, 5);
        val d: List<String> = listOf<String>("1", "2", "3", "4", "5");

        // 实例化 Set 集合（Set 和 List 的区别就是 Set 不可重复）
        val e = setOf<Int>(3, 2, 5, 1, 2, 3, 4); // 3 2 5 1 4
        val f: Set<Int> = setOf(3, 2, 5, 1, 2, 3, 4); // 3 2 5 1 4
        var eString = ""
        for(item in e) {
            eString += item;
        }
        appendMessage("$eString");

        // 实例化 Map 集合（字典表）
        val g: Map<String, String> = mapOf("k1" to "v1" , "k2" to "v2");
        val h = mapOf<Int, String>(1 to "v1" , 2 to "v2")
        appendMessage("${g["k1"]}, ${h[1]}"); // v1, v1

        // 上面声明的 List, Set, Map 都是不可变集合（即不能添加元素，不能删除元素，不能修改元素）
        // 如果要对集合增删改的话需要用 MutableList, MutableSet, MutableMap
        // 增删改的方法就是 add(), remove(), list[i] = xxx, set[i] = xxx, map[key] = value 之类的
        // 可以通过类似 toXXX() 的方法来做各种类型的转换
        var i: MutableList<Int> = mutableListOf(1, 2, 3);
        var j: MutableSet<Int> = mutableSetOf(1, 2, 3);
        var k: MutableMap<String, String> = mutableMapOf("k1" to "v1" , "k2" to "v2")
    }

    fun sample3() {
        // component1(), component2(), component3(), component4(), component5() - 获取第 n 个元素
        // elementAt(), indexOf(), indexOfFirst{}, indexOfLast{}
        // first(), first{}, firstOrNull(), firstOrNull{}, last(), last{}, lastOrNull(), lastOrNull{}, single(), single{}, singleOrNull(), singleOrNull{}
        // 上面 single 和 first 的区别是，如果匹配到多个结果的话，那么 single 是会抛出异常的

        // elementAtOrNull(), getOrNull() - 根据索引获取到的元素越界了，则返回 null
        // elementAtOrElse(index, {...}), getOrElse(index, {...}) - 根据索引获取到的元素越界了，则返回指定表达式的运算结果
        // forEachIndexed{}, forEachIndexed{} - 遍历元素
        val a = listOf(1, 2, 3, 4, 5);
        var aString1 = "";
        a.forEach{ aString1 += it };
        var aString2 = "";
        a.forEachIndexed { index, value -> aString2 += index; aString2 += value }
        appendMessage("${a.elementAtOrNull(5)}, ${a.elementAtOrElse(5, { it * 100 })}, $aString1, $aString2"); // null, 500, 12345, 0112233445
    }

    fun sample4() {
        val a = listOf(1, 2, 3, 4, 5, 5, 4, 3, 2, 1);

        // filter{} - 检索符合指定条件的数据，类似的还有 filterIndexed{}, filterNot{}, filterNotNull{}
        var b = a.filter { it % 2 == 0 }; // [2,4,4,2]

        // take() - 获取前 n 条数据，类似的有 takeLast()
        // takeWhile{} - 按顺序一条一条地拿数据，直到 while 条件为假就退出，类似的有 takeLastWhile{}
        var c = a.take(3); // [1,2,3]
        var d = a.takeWhile { it < 3 }; // [1,2]

        // drop() - 删除前 n 条数据，类似的有 dropLast();
        // dropWhile{} - 按顺序一条一条地删数据，直到 while 条件为假就退出，类似的有 dropLastWhile{}
        var e = a.drop(3); // [4,5,5,4,3,2,1]
        var f = a.dropWhile { it < 3 }; // [3,4,5,5,4,3,2,1]

        // distinct() - 直接排重
        // distinctBy{} - 根据表达式结果排重
        var g = a.distinct(); // [1,2,3,4,5]
        var h = a.distinctBy { it % 2 == 0 }; // [1,2]

        // slice() - 获取指定索引范围的数据
        var i = a.slice(7..9); // [3,2,1]

        appendMessage("$b, $c, $d, $e, $f, $g, $h, $i");
    }

    fun sample5() {
        // reversed() - 反序
        // sorted(), sortedBy{}, sortedDescending(), sortedByDescending{}

        val a = listOf(1, 2, 3, 4, 5);
        var b = a.sortedByDescending { it % 2 };
        var bString = "";
        b.forEach{ bString += it };
        appendMessage("$bString"); // 13524
    }

    fun sample6() {
        val a = listOf(1, 2, 3, 4, 5);

        // any() - 是否包含元素
        // any{} - 是否包含符合指定条件的元素
        // all{} - 集合中是否所有元素都符合指定的条件
        // none() - 是否为空集合
        // none{} - 集合中是否所有元素都不符合指定的条件
        // count(), count{} - 统计符合条件的元素的数量
        // max(), maxBy{}, min(), minBy{}, sum(), sumBy{}, sumByDouble{}, average()
        var b = a.any { it > 3 }; // true
        var c = a.count {it > 3}; // 2
        var d = a.all {it > 3}; // false
        var e = a.sumBy { if (it % 2 == 0) it else 0 }; // 6
        var f = a.average(); // 3.0

        // reduce{} - 从集合第一个元素到最后一个元素的累计操作，类似的还有 reduceIndexed{}, reduceRight{}, reduceRightIndexed{}
        // fold(){} - 与 reduce{} 类似，只不过 fold(){} 可以有个累计初始值，类似的还有 foldIndexed{}, foldRight{}, foldRightIndexed{}
        var g = a.reduce { result, next -> result + next }; //15
        var h = a.fold(100) { result, next -> result + next }; // 115
        appendMessage("$b, $c, $d, $e, $f, $g, $h");

        // contains() - 判断数组是否包含指定元素
        // in - 判断指定的元素是否在数组中
        // !in - 判断指定的元素是否不在数组中
        appendMessage("${a.contains(1)}, ${1 in a}, ${1 !in a}"); // true, true, false
    }

    fun sample7() {
        val a = listOf(1, 2, 3, 4, 5);

        // map{} - 把每个元素按照指定的表达式进行计算，然后将结果组成一个新的集合，类似的还有 mapNotNull{}, mapIndexed{}, mapIndexedNotNull{}
        // flatMap{} - 类似于 map{}，只不过 map{} 是合并多个转换后的元素为一个集合，而 flatMap{} 是合并多个转换后的集合为一个集合
        // groupBy{} - 按指定条件分组，返回的是一个 Map<K,List<T>>类型的集合
        var b = a.map { it * 2 }; // [2,4,6,8,10]
        var c = a.flatMap { listOf(0, 999, it * 2) }; // [0,999,2,0,999,4,0,999,6,0,999,8,0,999,10]
        var d = a.groupBy { if ( it % 2 == 0) "even" else "odd" }; // {odd=[1,3,5],even=[2,4]}

        appendMessage("$b, $c, $d");
    }

    fun sample8() {


        /*
        plus() : 合并两个集合中的元素，组成一个新的集合。也可以使用符号+
zip : 由两个集合按照相同的下标组成一个新集合。该新集合的类型是：List<Pair>
unzip : 和zip的作用相反。把一个类型为List<Pair>的集合拆分为两个集合。看下面的例子
partition : 判断元素是否满足条件把集合拆分为有两个Pair组成的新集合。
         */
        val a = listOf(1, 2, 3, 4, 5);
        val b = listOf("a", "b", "c");

        // 通过 + 或 plus 连接两个集合（其实 plus() 就是 +）
        var c = a + b; // [1,2,3,4,5,a,b,c]

        // zip() - 按照相同的索引合并两个集合，两个集合的元素的数量不一样时则自动丢弃多出来的元素，合并后的新集合的类型是 List<Pair>
        // zip(){} - 可以按指定的表达式合并两个集合
        var d = a.zip(b); // [(1,a),(2,b),(3,c)]
        var d1 = d[0].first; // 1
        var d2 = d[0].second; // a
        var e = a.zip(b) { it1, it2 -> it2 + it1}; // [a1,b2,c3]

        // unzip() - 与 zip() 相反，用于把一个 List<Pair> 分拆为两个集合
        var f = d.unzip(); // ([1,2,3],[a,b,c])
        var f1 = f.first; // [1,2,3]
        var f2 = f.second; //[a,b,c]

        // partition{} - 分区，用于将集合转换为 Pair 对象（first 是符合指定条件的，second 是不符合指定条件的）
        var g = a.partition { it % 2 == 0  }; // ([2,4],[1,3,5])
        var g1 = g.first; // [2,4]
        var g2 = g.second; // [1,3,5]

        appendMessage("$c, $d, $d1, $d2, $e, $f, $f1, $f2, $g, $g1, $g2");

    }

    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }
}