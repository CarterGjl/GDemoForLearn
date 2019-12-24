package aop.demo.jetpack.android.jetpack.rx

interface Emitter<T> {

    fun onNext(value: T)

    fun onError(err: Throwable)

    fun onComplete()
}