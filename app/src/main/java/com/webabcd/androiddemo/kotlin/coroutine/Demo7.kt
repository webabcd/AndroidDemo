package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo6.*
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.*
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.button1
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.button2
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.button3
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.button4
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.button5
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.button6
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo7.textView1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Demo7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo7)

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

    fun myMap(input: Int): String {
        return "map $input"
    }
    fun myFilter(input: Int): Boolean {
        return input > 10
    }
    fun sample3() {
        // onEach
        // flowOf(1..100)
        CoroutineScope(Dispatchers.Default).launch {
            (1..100)
                .asFlow()
                .take(20)
                .filter { value ->
                    myFilter(value)
                }.map { value ->
                    myMap(value)
                }.collect { value ->
                    appendMessage(value)
                }

            (1..100)
                .asFlow()
                .transform { value ->
                    emit( "transform: $value")
                }.collect { value ->
                    appendMessage(value)
                }

            var result =
                (1..10)
                    .reduce { a, b ->
                        appendMessage("$a, $b")
                        a + b
                    }
            appendMessage("$result")
        }
    }

    fun sample4() {

    }

    fun sample5() {
        val flow = flow {
            for (i in 1..3) {
                delay(500)
                emit("emit: $i")
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            flow
                .buffer()
                .collect { value ->
                    delay(500)
                    appendMessage(value)
                }

        }
    }

    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // wait 500 ms
        emit("$i: Second")
    }
    fun sample6() {

        val flow = flow<Int> {
            for (i in 1..3) {
                delay(500)
                emit(i)
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            /*
            val flow1 = (1..3).asFlow()
            val flow2 = flowOf("a", "b", "c")
            flow1.zip(flow2) { a, b ->
                "$a -> $b"
            }.collect { appendMessage(it) }


            flow1.combine(flow2) { a, b ->
                "$a -> $b"
            }.collect { appendMessage(it) }

             */



            (1..3).asFlow().flatMapConcat {
                requestFlow(it)
            }
                .collect { value -> // collect and print
                    appendMessage("$value")
                }
        }
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