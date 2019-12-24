package aop.demo.jetpack.android.kotlin.impl.ui.data.model

import androidx.lifecycle.MutableLiveData

data class Listing<T>(
        val pagedList: MutableLiveData<T>,
        val loadStatus: MutableLiveData<Resource<String>>
        )