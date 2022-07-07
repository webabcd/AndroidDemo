package com.webabcd.androiddemo.jetpack.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_jetpack_lifecycle_livedatademo.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class LiveDataDemo : AppCompatActivity() {

    private lateinit var viewModel:LiveDataDemo_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_lifecycle_livedatademo)

        viewModel = ViewModelProvider(this, LiveDataDemo_ViewModelFactory()).get(LiveDataDemo_ViewModel::class.java)
        viewModel.currentName.observe(this) {
            textView1.setText("$it")
        }

        button1.setOnClickListener {
            val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
            val time = dateFormat.format(Date());
            viewModel._myName.postValue("$time")
        }
    }
}

class LiveDataDemo_ViewModel: ViewModel() {

    val _myName = MutableLiveData<String>()

    val currentName: LiveData<String>
        get() = _myName

    init {
        _myName.value = "James Doe"
    }

    fun setNewName(name: String) {
        _myName.value = name
    }
}

class LiveDataDemo_ViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveDataDemo_ViewModel::class.java)){
            return LiveDataDemo_ViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}