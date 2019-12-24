package aop.demo.jetpack.android.jetpack.rx

interface ObservableSource<T> {
    fun subscribe(observer: Observer<T>)
}