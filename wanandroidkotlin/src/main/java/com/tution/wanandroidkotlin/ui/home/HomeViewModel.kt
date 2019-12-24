package com.tution.wanandroidkotlin.ui.home

import androidx.lifecycle.MutableLiveData
import com.tution.wanandroidkotlin.base.BaseViewModel
import com.tution.wanandroidkotlin.bean.ArticleList
import com.tution.wanandroidkotlin.bean.Banner
import com.tution.wanandroidkotlin.repository.HomeRepository
import com.tution.wanandroidkotlin.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val repository by lazy {
        HomeRepository()
    }
    val mBanners: MutableLiveData<List<Banner>> = MutableLiveData()
    fun getBanners() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getBanners() }
            executeResponse(result, { mBanners.value = result.data }, {})
        }
    }

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    //    val mBanners :LiveData<List<Banner>> =liveData {
//        kotlin.runCatching {
//            val data = withContext(Dispatchers.IO) { repository.getBanners() }
//            emit(data.data)
//        }
//    }
    fun getArticleList(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getArticleList(page) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                if (boolean) repository.collectArticle(articleId)
                else repository.unCollectArticle(articleId)
            }
        }
    }
}