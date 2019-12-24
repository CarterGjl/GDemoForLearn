package aop.demo.jetpack.android.jetpackcompose

abstract class AbstractStrategy {

    abstract fun process(): Unit

    val bookingOrderStrategy:BookingOrderStrategy by lazy {
        BookingOrderStrategy()
    }
}