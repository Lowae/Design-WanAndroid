package com.lowe.wanandroid.ui.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemHomeArticleLayoutV2Binding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.ViewBindingHolder

/**
 * 文章[PagingItemViewBinder]
 */
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

/**
 * 文章行为
 */
sealed interface ArticleAction {

    /**
     * 主体点击
     */
    data class ItemClick(val position: Int, val article: Article) : ArticleAction

    /**
     * 收藏点击
     */
    data class CollectClick(val position: Int, val article: Article) : ArticleAction

    /**
     * 作者点击
     */
    data class AuthorClick(val position: Int, val article: Article) : ArticleAction
}