package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

class HelloWorld : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_helloworld)

        button1.setOnClickListener {
            textView1.text = "hello world";
        }
    }
}
