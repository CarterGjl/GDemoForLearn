package aop.demo.jetpack.android.kotlin.impl.view_model

import androidx.lifecycle.MutableLiveData
import aop.demo.jetpack.android.kotlin.impl.repository.model.ArticleBean
import aop.demo.jetpack.android.kotlin.impl.repository.model.ArticleResponseBody
import aop.demo.jetpack.android.kotlin.impl.repository.model.HttpResponse
import aop.demo.jetpack.android.kotlin.impl.repository.remote.RetrofitClient
import aop.demo.jetpack.android.kotlin.impl.ui.data.model.Listing
import aop.demo.jetpack.android.kotlin.impl.ui.data.model.Resource
import aop.demo.jetpack.android.kotlin.impl.ui.ui.adapter.ArticleAdapter
import aop.demo.jetpack.android.kotlin.module_library.BaseViewModel
import aop.demo.jetpack.android.kotlin.module_library.helper.RxHelper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.functions.Consumer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollectViewModel : BaseViewModel() {
    var mPage = 0
    val collectionList = MutableLiveData<HttpResponse<ArticleResponseBody<ArticleBean>>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun getCollect(): Listing<HttpResponse<ArticleResponseBody<ArticleBean>>> {
        loadStatus.postValue(Resource.loading())
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).getCollectList(mPage)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    if(it.data != null){
                        loadStatus.postValue(Resource.success())
                        collectionList.value = it
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
        return Listing(collectionList,loadStatus)
    }

    fun collect(id:Int,collectionObserver: ArticleAdapter.CollectionObserver<HttpResponse<Any>>) {
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).collect(id)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    collectionObserver.onChanged(it)
                }, {
                    collectionObserver.onChanged(null)
                })
        addDisposable(subscribe)
    }
    fun unCollect(id:Int,collectionObserver: ArticleAdapter.CollectionObserver<HttpResponse<Any>>) {
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).unCollect(id)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    collectionObserver.onChanged(it)
                }, {
                    collectionObserver.onChanged(null)
                })
        addDisposable(subscribe)
    }


    fun getSomeTing(){


    }
}