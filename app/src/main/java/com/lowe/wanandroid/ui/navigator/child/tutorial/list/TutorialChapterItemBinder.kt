package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemHomeArticleLayoutV2Binding
import com.lowe.wanandroid.services.model.Article

class TutorialChapterItemBinder(private val onClick: (Int, Article) -> Unit) :
    ItemViewBinder<Article, ViewBindingHolder<ItemHomeArticleLayoutV2Binding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemHomeArticleLayoutV2Binding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_home_article_layout_v2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemHomeArticleLayoutV2Binding>,
        item: Article
    ) {
        holder.binding.apply {
            ivCollect.isVisible = false
            root.setOnClickListener { onClick(holder.bindingAdapterPosition, item) }
            article = item
            executePendingBindings()
        }
    }
}