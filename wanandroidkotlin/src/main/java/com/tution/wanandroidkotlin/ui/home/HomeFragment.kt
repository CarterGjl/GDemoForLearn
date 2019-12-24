package com.tution.wanandroidkotlin.ui.home

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tution.wanandroidkotlin.R
import com.tution.wanandroidkotlin.base.BaseVMFragment
import com.tution.wanandroidkotlin.bean.ArticleList
import com.tution.wanandroidkotlin.bean.Banner
import com.tution.wanandroidkotlin.bean.User
import com.tution.wanandroidkotlin.bean.WanResponse
import com.tution.wanandroidkotlin.http.RetrofitClient
import com.tution.wanandroidkotlin.ui.home.adapter.HomeArticleAdapter
import com.tution.wanandroidkotlin.util.GlideImageLoader
import com.tution.wanandroidkotlin.util.Preference
import com.tution.wanandroidkotlin.util.dp2px
import com.tution.wanandroidkotlin.util.onNetError
import com.tution.wanandroidkotlin.view.CustomLoadMoreView
import com.tution.wanandroidkotlin.view.SpaceItemDecoration
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val banner by lazy { com.youth.banner.Banner(activity) }
    private val homeArticleAdapter by lazy { HomeArticleAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    override fun initData() {

        mViewModel.getBanners()
    }

    private var currentPage = 0
    override fun initView() {
        homeRecycleView.run {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10)))
        }
        initBanner()
        initAdapter()

        homeRefreshLayout.run {
            setOnRefreshListener { refresh() }
            isRefreshing = true
        }
        refresh()
    }

    fun refresh() {
        homeArticleAdapter.setEnableLoadMore(false)
        homeRefreshLayout.isRefreshing = true
        currentPage = 0
        mViewModel.getArticleList(currentPage)

        val mainScope = MainScope()
        mainScope.launch(Dispatchers.Main) {
            try {
                val fetchData = fetchData()
                Log.d("result", fetchData.toString())

            }catch (e:Exception){
            }
        }
//        mainScope.launch(Dispatchers.Main) {
//            val fetchBanner = fetchBanner()
//            setBanner(fetchBanner.data)
//            Log.d("result", fetchBanner.toString())
//        }
    }

    private suspend fun fetchBanner(): WanResponse<List<Banner>> {
        return withContext(Dispatchers.IO) {
            RetrofitClient.reqApi.getBannerNewAsync().await()
        }
    }

    private suspend fun fetchData(): WanResponse<ArticleList> {
        return withContext(Dispatchers.IO) {
            val await = RetrofitClient.reqApi.getHomeArticlesAsync(1).await()
            await

        }
    }

    private fun initAdapter() {
        homeArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                //                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to homeArticleAdapter.data[position].link)
            }
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, homeRecycleView)
        }
        homeRecycleView.adapter = homeArticleAdapter
    }

    private fun loadMore() {
        mViewModel.getArticleList(currentPage)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mBanners.observe(this@HomeFragment, Observer { it ->
                it?.let { setBanner(it) }
            })
            mArticleList.observe(this@HomeFragment, Observer { it ->
                it?.let { setArticles(it) }
            })
        }
    }

    private fun setArticles(articleList: ArticleList) {
        homeArticleAdapter.run {
            if (homeRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        homeRefreshLayout.isRefreshing = false
        currentPage++
    }

    private fun setBanner(bannerList: List<Banner>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        banner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setDelayTime(3000)
        banner.start()
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    homeArticleAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
//                    activity?.startKtxActivity<LoginActivity>()
                }
            }
        }
    }

    private fun initBanner() {

        banner.run {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200))
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    //                    startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to bannerUrls[position])
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun providerVMClass(): Class<HomeViewModel>? {
        return HomeViewModel::class.java
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        activity?.onNetError(e) {
            homeRefreshLayout.isRefreshing = false
        }
    }
}