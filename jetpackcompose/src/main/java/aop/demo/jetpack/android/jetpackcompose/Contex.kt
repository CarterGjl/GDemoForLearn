package aop.demo.jetpack.android.jetpackcompose

import kotlinx.coroutines.*

class Contex(private val abstractStrategy: AbstractStrategy) {

    fun process(): Unit {
        abstractStrategy.process()
    }

}
val thread = object : Thread(){
    override fun run() {
        super.run()
        sleep(1000)
        println("finish"+System.currentTimeMillis())
    }
}

suspend fun main() {

    Contex(BookingOrderStrategy()).process()

    println("start"+System.currentTimeMillis())
    thread.start()
    println(1)
    val launch = GlobalScope.launch(Dispatchers.Main+CoroutineName("hello"),start = CoroutineStart
            .DEFAULT) {
        println(2)
    }
    println(3)
    launch.join()
    println(4)

}
