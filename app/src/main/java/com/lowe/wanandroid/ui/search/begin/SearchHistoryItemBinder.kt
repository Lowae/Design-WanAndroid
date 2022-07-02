package com.lowe.wanandroid.ui.search.begin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemSearchHistoryLayoutBinding
import com.lowe.wanandroid.ui.ViewBindingHolder
import com.lowe.wanandroid.ui.search.SearchState

class SearchHistoryItemBinder(
    private val itemClick: (Int, SearchState) -> Unit,
    private val deleteClick: (Int, SearchState) -> Unit
) :
    ItemViewBinder<SearchState, ViewBindingHolder<ItemSearchHistoryLayoutBinding>>() {
    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemSearchHistoryLayoutBinding>,
        item: SearchState
    ) {
        holder.binding.apply {
            searchHistory = item.keywords
            root.setOnClickListener { itemClick(holder.bindingAdapterPosition, item) }
            historyDelete.setOnClickListener { deleteClick(holder.bindingAdapterPosition, item) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemSearchHistoryLayoutBinding> =
        ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_search_history_layout,
                parent,
                false
            )
        )
}