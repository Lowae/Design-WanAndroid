package com.lowe.wanandroid.ui.collect.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemCollectListArticleLayoutBinding
import com.lowe.wanandroid.services.model.CollectBean
import com.lowe.wanandroid.ui.ItemClickType
import com.lowe.wanandroid.ui.ViewBindingHolder

class CollectItemBinder(private val onClick: (Int, CollectBean, type: ItemClickType) -> Unit) :
    PagingItemViewBinder<CollectBean, ViewBindingHolder<ItemCollectListArticleLayoutBinding>>() {

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemCollectListArticleLayoutBinding>,
        item: CollectBean
    ) {
        holder.binding.apply {
            collect = item
            root.setOnClickListener { onClick(holder.bindingAdapterPosition, item, ItemClickType.CONTENT) }
            ivCollect.setOnClickListener { onClick(holder.bindingAdapterPosition, item, ItemClickType.COLLECT) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemCollectListArticleLayoutBinding> = ViewBindingHolder(
        DataBindingUtil.inflate(
            inflater,
            R.layout.item_collect_list_article_layout,
            parent,
            false
        )
    )
}