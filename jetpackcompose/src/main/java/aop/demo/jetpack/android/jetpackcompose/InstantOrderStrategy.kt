package aop.demo.jetpack.android.jetpackcompose

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class InstantOrderStrategy: AbstractStrategy() {
    override fun process() {
        println("Instant cancel")
    }
}

suspend fun main() {


    GlobalScope.launch(MyContinuationInterceptor()) {
        println(1)
        val deferred = async {
            println("2 ${Thread.currentThread().name}")
            delay(1000)
            println("3${Thread.currentThread().name}")
            "hello"
        }
        println("4${Thread.currentThread().name}")
        val result = deferred.await()
        println("5. $result${Thread.currentThread().name}")
    }.join()
    println(6)
}

class MyContinuationInterceptor : ContinuationInterceptor{
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor //To change initializer of created properties use File | Settings | File
    // Templates.

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return MyContinuation(continuation)
    }

}

class MyContinuation<T>(private val continuation: Continuation<T>):Continuation<T>{
    override val context: CoroutineContext
        get() = continuation.context //To change initializer of created properties use File | Settings |
                // File
    // Templates.

    override fun resumeWith(result: Result<T>) {
        continuation.resumeWith(result)
    }

}