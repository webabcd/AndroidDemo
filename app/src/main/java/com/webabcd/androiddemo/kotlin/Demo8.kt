/**
 * 本例用于演示类继承，接口，抽象类
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

class Demo8 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo8)

        sample1() // 类继承
        sample2() // 接口
        sample3() // 抽象类
    }

    fun sample1() {
        var a = Demo8_Class1(40);
        appendMessage(a.hello()) // hello:wanglei, age:40, country:china

        var b = Demo8_Class2(40, "中国");
        appendMessage(b.hello()) // hi:wanglei, age:80, country:中国
    }

    fun sample2() {
        var a = Demo8_Class3(40)
        appendMessage(a.hello()) // hello:wanglei, age:40 hi:wanglei, age:40
    }

    fun sample3() {
        var a = Demo8_Class4("wanglei")
        appendMessage(a.hello()) // hello:wanglei, age:100, country:china
    }


    fun appendMessage(message: String) {
        textView1.append(message)
        textView1.append("\n")
    }
}