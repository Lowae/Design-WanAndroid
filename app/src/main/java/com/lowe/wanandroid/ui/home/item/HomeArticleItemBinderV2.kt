package com.lowe.wanandroid.ui.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemHomeArticleLayoutV2Binding
import com.lowe.wanandroid.services.model.Article

class HomeArticleItemBinderV2(private val onClick: (ArticleAction) -> Unit) :
    PagingItemViewBinder<Article, ViewBindingHolder<ItemHomeArticleLayoutV2Binding>>() {

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
            root.setOnClickListener {
                onClick(
                    ArticleAction.ItemClick(
                        holder.bindingAdapterPosition,
                        item
                    )
                )
            }
            ivCollect.setOnClickListener {
                onClick(
                    ArticleAction.CollectClick(
                        holder.bindingAdapterPosition,
                        item
                    )
                )
            }
            tvAuthor.setOnClickListener {
                onClick(
                    ArticleAction.AuthorClick(
                        holder.bindingAdapterPosition,
                        item
                    )
                )
            }
            article = item
            executePendingBindings()
        }
    }
}

sealed interface ArticleAction {

    data class ItemClick(val position: Int, val article: Article) : ArticleAction

    data class CollectClick(val position: Int, val article: Article) : ArticleAction

    data class AuthorClick(val position: Int, val article: Article) : ArticleAction
}