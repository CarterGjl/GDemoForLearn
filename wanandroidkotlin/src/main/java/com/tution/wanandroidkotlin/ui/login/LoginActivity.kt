package com.tution.wanandroidkotlin.ui.login

import android.app.ProgressDialog
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.tution.wanandroidkotlin.R
import com.tution.wanandroidkotlin.base.BaseVMActivity
import com.tution.wanandroidkotlin.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*

class LoginActivity : BaseVMActivity<LoginViewModel>() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, getLayoutResId())
    }

    override fun initView() {
        mToolbar.title = "登录"
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
    }
    override fun initData() {
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        login.setOnClickListener {
            onClickListener
        }
    }


    override fun startObserve() {
//        super.startObserve()
        mViewModel.apply {
            mRegisterUser.observe(this@LoginActivity, Observer {
                it?.run {
                    mViewModel.login(username,password)
                }
            })

            uiState.observe(this@LoginActivity, Observer {
                if (it.showProgress){
                    showProgressDialog()
                }
                it.showSuccess?.let {
                    dismissProgressDialog()
                    TODO("开启新的页面")
//                    startKtxActivity<NewMainActivity>()
                    finish()
                }
            })
        }
    }
    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }
    private fun login() {
        mViewModel.login(userNameEt.text.toString(), passwordEt.text.toString())
    }

    private fun register() {
        mViewModel.register(userNameEt.text.toString(), passwordEt.text.toString())
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.login -> login()
            R.id.register -> register()
        }
    }
    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
    override fun providerVMClass(): Class<LoginViewModel>? {
        return LoginViewModel::class.java
    }


}