package com.lowe.wanandroid.ui.dashboard.child.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.drakeet.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemProjectArticleLayoutBinding
import com.lowe.wanandroid.services.model.Article

class ProjectChildItemBinder :
    ItemViewBinder<Article, ViewBindingHolder<ItemProjectArticleLayoutBinding>>() {
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
            article = item
            executePendingBindings()
        }
    }


}