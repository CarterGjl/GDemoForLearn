package aop.demo.jetpack.android.jetpackcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.function.Consumer
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnLaunch.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                println("thread ${Thread.currentThread().name}")
                userName.text = getName()
            }
        }
//        thread.start()
    }

    private suspend fun getName()= suspendCoroutine<String> {
        println("thread ${Thread.currentThread().name}")
        Thread.sleep(2000)
        println("thread ${Thread.currentThread().name}")
        it.resume("协程")
    }

    fun getUserName(callback: Callback<String>): Unit {

    }

    suspend fun getUserNameCon() = suspendCoroutine<String> {
        getUserName(object :Callback<String>{
            override fun onError(t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onSuccess(value: String) {
                it.resume(value)
            }

        })
    }
}

interface Callback<T>{
    fun onSuccess(value:T)
    fun onError(t: Throwable)
}

//val thread = object : Thread(){
//    override fun run() {
//        super.run()
//
//        sleep(1000)
//
//        println("fadsfa")
//    }
//}
fun main() {


}