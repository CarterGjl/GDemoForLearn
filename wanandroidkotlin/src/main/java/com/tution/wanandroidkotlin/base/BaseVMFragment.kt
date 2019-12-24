package com.tution.wanandroidkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseVMFragment<VM:BaseViewModel>(useeBinding:Boolean = false):Fragment() {
    protected lateinit var mViewModel: VM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(getLayoutResId(), container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    protected open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}

    abstract fun initData()

    abstract fun initView()

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }
    open fun providerVMClass(): Class<VM>? = null
    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }
}