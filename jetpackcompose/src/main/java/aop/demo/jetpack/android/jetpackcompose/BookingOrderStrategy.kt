package aop.demo.jetpack.android.jetpackcompose

class BookingOrderStrategy: AbstractStrategy() {
    override fun process() {
        println("booking cancel")
    }
}