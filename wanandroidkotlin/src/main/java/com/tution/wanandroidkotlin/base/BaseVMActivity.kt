package com.tution.wanandroidkotlin.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseVMActivity<VM:BaseViewModel> :AppCompatActivity(),LifecycleObserver{

    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        startObserve()
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutResId(): Int


    private fun initVM(){
        providerVMClass()?.let { it ->
            mViewModel = ViewModelProvider(this).get(it)
            mViewModel.let {
                lifecycle.addObserver(it)
            }
        }
    }
    open fun providerVMClass():Class<VM>?=null

    open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}

    override fun onDestroy() {
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}