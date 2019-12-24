package aop.demo.jetpack.android.kotlin.impl.ui.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import aop.demo.jetpack.android.kotlin.R
import aop.demo.jetpack.android.kotlin.impl.repository.model.ArticleBean
import aop.demo.jetpack.android.kotlin.impl.repository.model.HttpResponse
import aop.demo.jetpack.android.kotlin.impl.ui.ui.login.LoginActivity
import aop.demo.jetpack.android.kotlin.impl.view_model.CollectViewModel
import aop.demo.jetpack.android.kotlin.module_library.util.PreferencesUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context1: Context

    var mutableList: MutableList<ArticleBean> = mutableListOf()


    fun setData(mutableList: List<ArticleBean>): Unit {
        this.mutableList = mutableList as MutableList<ArticleBean>
    }

    fun addData(x: ArticleBean): Unit {
        this.mutableList.add(x)
    }


    fun addDataAll(data: List<ArticleBean>) {
        mutableList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context1 = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view = view)
    }


    override fun getItemCount(): Int {
        return this.mutableList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.bindData(context1, mutableList[position])
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var isCollectionList: Boolean = false

        private var collectionViewModel: CollectViewModel = CollectViewModel()

        private val mRlHeader: RelativeLayout
        private val mIvNew: ImageView
        private val mTvTagName: TextView
        private val mIvImage: ImageView
        private val mTvTitle: TextView
        private val mIvCollect: CheckBox
        private val mTvTime: TextView
        private val mTvAuthor: TextView

        init {

            mRlHeader = view.findViewById(R.id.rl_header)
            mIvNew = view.findViewById(R.id.iv_new)
            mTvTagName = view.findViewById(R.id.tv_tag_name)
            mIvImage = view.findViewById(R.id.iv_image)
            mTvTitle = view.findViewById(R.id.tv_title)
            mIvCollect = view.findViewById(R.id.iv_collect)
            mTvTime = view.findViewById(R.id.tv_time)
            mTvAuthor = view.findViewById(R.id.tv_author)

        }

        fun bindData(context1: Context, articleBean: ArticleBean) {
            mTvTitle.text = articleBean.title
            mTvTime.text = articleBean.niceDate
            mTvAuthor.text = articleBean.author
            if (TextUtils.isEmpty(articleBean.envelopePic)) {
                mIvImage.visibility = View.GONE
            } else {
                mIvImage.visibility = View.VISIBLE
                Glide.with(mIvImage.context)
                        .load(articleBean.envelopePic)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(mIvImage)
            }

            mTvTagName.text = articleBean.chapterName

            if (articleBean.tags != null && articleBean.tags?.size!! > 0) {
                mTvTagName.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
                mTvTagName.paint.isAntiAlias = true//抗锯齿
                mTvTagName.setOnClickListener {
                    //                    WebViewActivity.loadUrl(
//                            tagTextView.context,
//                            RetrofitClient.WAN_BASE_URL + articleBean.tags?.get(0)?.url,
//                            articleBean.chapterName
//                    )
                }
            }


            if (articleBean.fresh) {
                mIvNew.visibility = View.VISIBLE
            } else {
                mIvNew.visibility = View.GONE
            }
            mIvCollect.isChecked = articleBean.collect
            if (isCollectionList) {
                mIvCollect.isChecked = true
                articleBean.id = articleBean.originId!!
            }
            mIvCollect.setOnClickListener {
                var isLogin: Boolean by PreferencesUtil("login", false)
                if (isLogin) {
                    if (mIvCollect.isChecked) {
                        collectionViewModel.collect(articleBean.id, object : CollectionObserver<HttpResponse<Any>> {
                            override fun onChanged(t: HttpResponse<Any>?) {
                                if (t?.errorCode == 0) {

//                                    ToastUtils.showToast("收藏成功")
                                } else {
//                                    ToastUtils.showToast("收藏失败")
                                }
                            }
                        })
                    } else {
                        collectionViewModel.unCollect(articleBean.id, object : CollectionObserver<HttpResponse<Any>> {
                            override fun onChanged(t: HttpResponse<Any>?) {
                                if (t?.errorCode == 0) {
//                                    ToastUtils.showToast("取消收藏成功")
                                } else {
//                                    ToastUtils.showToast("取消收藏失败")
                                }
                            }
                        })
                    }
                } else {
                    val intent = Intent(context1, LoginActivity::class.java)
                    context1.startActivity(intent)
                }

            }
        }
    }


    interface CollectionObserver<T> {
        fun onChanged(t: T?)
    }
}