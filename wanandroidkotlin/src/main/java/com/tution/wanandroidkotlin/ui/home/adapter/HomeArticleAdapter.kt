package com.tution.wanandroidkotlin.ui.home.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tution.wanandroidkotlin.R
import com.tution.wanandroidkotlin.bean.Article
import com.tution.wanandroidkotlin.util.fromN

@Suppress("DEPRECATION")
class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) : BaseQuickAdapter<Article, BaseViewHolder>
(layoutResId) {

    private var showStar = true

    fun showStar(showStar: Boolean) {
        this.showStar = showStar
    }

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.articleTitle, if (fromN()) Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(item.title))
                .setText(R.id.articleAuthor, item.author)
                .setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")
                .setText(R.id.articleTime, item.niceDate)
                .addOnClickListener(R.id.articleStar)
        helper.addOnClickListener(R.id.articleStar)
        if (showStar) helper.setImageResource(R.id.articleStar, if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal)
        else helper.setVisible(R.id.articleStar, false)
    }
}