package com.tution.wanandroidkotlin.repository

import com.tution.wanandroidkotlin.bean.ArticleList
import com.tution.wanandroidkotlin.bean.Banner
import com.tution.wanandroidkotlin.bean.WanResponse
import com.tution.wanandroidkotlin.http.RetrofitClient
import kotlinx.coroutines.Deferred

class HomeRepository : BaseRepository() {

    suspend fun getBanners(): WanResponse<List<Banner>> {
        return RetrofitClient.reqApi.getBannerNewAsync().await()
    }

    suspend fun getArticleList(page: Int): WanResponse<ArticleList> {
        return RetrofitClient.reqApi.getHomeArticlesAsync(page).await()
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall {  RetrofitClient.reqApi.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall {  RetrofitClient.reqApi.cancelCollectArticle(articleId) }
    }
}