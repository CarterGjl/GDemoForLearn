package aop.demo.jetpack.android.jetpack

import org.junit.Test

import org.junit.Assert.*

class ExampleTest {

    @Test
    fun getP() {
    }

    @Test
    fun setP() {
    }
    @Test
    fun test(){
        val e = Example()
        e.p = "new"
        println(e.p)
    }
}