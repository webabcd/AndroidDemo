package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Demo6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo6)

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