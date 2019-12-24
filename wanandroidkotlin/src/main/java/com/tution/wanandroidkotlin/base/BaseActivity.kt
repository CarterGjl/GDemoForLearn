package com.tution.wanandroidkotlin.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        iniData()
    }

    abstract fun iniData()

    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}