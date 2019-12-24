package aop.demo.jetpack.android.jetpack.rx

interface Observer<T> {
    
    fun onSubscribe()

    fun onNext(value: T)

    fun onError(err: Throwable)

    fun onComplete()
}