package aop.demo.jetpack.android.kotlin.impl.view_model

import androidx.lifecycle.MutableLiveData
import aop.demo.jetpack.android.kotlin.impl.repository.model.ArticleBean
import aop.demo.jetpack.android.kotlin.impl.repository.model.ArticleResponseBody
import aop.demo.jetpack.android.kotlin.impl.repository.model.HttpResponse
import aop.demo.jetpack.android.kotlin.impl.repository.remote.RetrofitClient
import aop.demo.jetpack.android.kotlin.impl.ui.data.model.Listing
import aop.demo.jetpack.android.kotlin.impl.ui.data.model.Resource
import aop.demo.jetpack.android.kotlin.module_library.BaseViewModel
import aop.demo.jetpack.android.kotlin.module_library.helper.RxHelper

class CategoryViewModel: BaseViewModel() {
    var mPage = 0
    val pagedList = MutableLiveData<HttpResponse<ArticleResponseBody<ArticleBean>>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun getCategory(id: Int): Listing<HttpResponse<ArticleResponseBody<ArticleBean>>> {
        loadStatus.postValue(Resource.loading())
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).getCategory(mPage,id)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe({
                if(it.data != null){
                    loadStatus.postValue(Resource.success())
                    pagedList.value = it
                }else{
                    loadStatus.postValue(Resource.error())
                }

            }, {
                if (mPage > 0) {
                    mPage--
                }
                loadStatus.postValue(Resource.error())
            })
        addDisposable(subscribe)
        return Listing(pagedList,loadStatus)
    }


}