package aop.demo.jetpack.android.kotlin.impl.ui.ui.aticle

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import aop.demo.jetpack.android.kotlin.R
import aop.demo.jetpack.android.kotlin.impl.repository.model.TreeBean
import aop.demo.jetpack.android.kotlin.impl.ui.data.model.Status.*
import aop.demo.jetpack.android.kotlin.impl.ui.ui.adapter.ArticleAdapter
import aop.demo.jetpack.android.kotlin.impl.ui.ui.widget.LoadMoreAdapter
import aop.demo.jetpack.android.kotlin.impl.ui.ui.widget.LoadMoreWrapper
import aop.demo.jetpack.android.kotlin.impl.view_model.CategoryViewModel
import aop.demo.jetpack.android.kotlin.module_library.BaseActivity
import kotlinx.android.synthetic.main.category_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagArticleActivity : BaseActivity<CategoryViewModel>() {

    private var mEnabled: LoadMoreAdapter.Enabled? = null

    private val mAdapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }


    private var treeBean: TreeBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)
        treeBean = intent.getSerializableExtra("TagBean") as TreeBean
        if (treeBean == null) {
            finish()
        }
        if (treeBean != null) {
            title = treeBean!!.name
            initData()
            getCategorys(treeBean!!.id)
        }
    }

    private fun initData() {
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        LoadMoreWrapper.with(mAdapter)
                .setShowNoMoreEnabled(true)
                .setListener { enabled ->
                    getCategorys(treeBean!!.id)
                    mEnabled = enabled
                }
                .into(mRecyclerView)
        mSwipeRefreshLayout.setOnRefreshListener {
            mSwipeRefreshLayout.isRefreshing = true
            viewModel.mPage = 0
            getCategorys(treeBean!!.id)
        }
        viewModel.apply {
            loadStatus.observe(this@TagArticleActivity, Observer {
                when (it.status) {
                    SUCCESS -> {
                        mEnabled?.setLoadFailed(false)
                        showContentView()
                    }
                    ERROR -> mEnabled?.setLoadFailed(true)
                    LOADING -> {

                    }
                }
            })

            pagedList.observe(this@TagArticleActivity, Observer {
                mSwipeRefreshLayout.isRefreshing = false
                if (it==null){
                    return@Observer
                }
                if (viewModel.mPage==0){
                    mAdapter.setData(it.data?.datas!!)

                    return@Observer
                }
                mAdapter.addDataAll(it.data?.datas!!)
                if(it.data?.datas?.size!! < it.data?.size!!){
                    mEnabled?.loadMoreEnabled = false
                }else{
                    mEnabled?.setLoadFailed(false)
                }
            })
        }
    }

    private  fun getCategorys(id: Int) {
        viewModel.getCategory(id)
    }
}