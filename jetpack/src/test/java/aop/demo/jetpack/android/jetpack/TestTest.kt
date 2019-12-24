package aop.demo.jetpack.android.jetpack

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class TestTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test1() {
        val test = aop.demo.jetpack.android.jetpack.Test()
        test.test()
    }
}