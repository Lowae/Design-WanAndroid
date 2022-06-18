package com.lowe.wanandroid.ui.project.child.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemProjectArticleLayoutBinding
import com.lowe.wanandroid.services.model.Article

class ProjectChildItemBinder(private val onClick: (Int, Article) -> Unit) :
    PagingItemViewBinder<Article, ViewBindingHolder<ItemProjectArticleLayoutBinding>>() {
    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemProjectArticleLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_project_article_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemProjectArticleLayoutBinding>,
        item: Article
    ) {
        holder.binding.apply {
            onClickFunc = onClick
            article = item
            executePendingBindings()
        }
    }
}