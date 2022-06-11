package com.lowe.wanandroid.ui.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ItemViewDataBindingBinder
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemHomeArticleLayoutBinding
import com.lowe.wanandroid.services.model.Article

class HomeArticleItemBinder(private val onClick: (Pair<Int, Article>) -> Unit) :
    ItemViewDataBindingBinder<Article, ViewBindingHolder<ItemHomeArticleLayoutBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemHomeArticleLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_home_article_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemHomeArticleLayoutBinding>,
        item: Article
    ) {
        super.onBindViewHolder(holder, item)
        holder.binding.apply {
            article = item
            executePendingBindings()
        }
    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)
        onClick(position to adapterItems[position] as Article)
    }
}