



















package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import com.webabcd.androiddemo.kotlin.HelloWorld_Java
import com.webabcd.androiddemo.kotlin.HelloWorld_Kotlin
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo2.*
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.*
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button1
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button2
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button3
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button4
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button5
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button6
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.textView1
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.measureTimeMillis

class Demo3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo3)


        button1.setOnClickListener {
            sample1()
        }

        button2.setOnClickListener {
            sample2()
        }

        button3.setOnClickListener {
            sample3()
        }

        button4.setOnClickListener {
            sample4()
        }

        button5.setOnClickListener {
            sample5()
        }

        button6.setOnClickListener {
            sample6()
        }
    }

    fun sample1() {

    }

    fun sample2() {

    }

    fun sample3() {

    }

    fun sample4() {

    }

    fun sample5() {

    }

    fun sample6() {

    }

    fun appendMessage(message: String) {
        val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
        val time = dateFormat.format(Date());
        val threadName = Thread.currentThread().name

        CoroutineScope(Dispatchers.Main).launch{
            val log = "$time: $message（$threadName）"
            textView1.append(log);
            textView1.append("\n");

            println(log)
        }
    }
}