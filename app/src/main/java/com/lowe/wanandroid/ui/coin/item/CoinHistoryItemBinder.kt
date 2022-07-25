package com.lowe.wanandroid.ui.coin.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemCoinHistoryLayoutBinding
import com.lowe.common.services.model.CoinHistory
import com.lowe.wanandroid.ui.ViewBindingHolder

class CoinHistoryItemBinder :
    PagingItemViewBinder<CoinHistory, ViewBindingHolder<ItemCoinHistoryLayoutBinding>>() {
    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemCoinHistoryLayoutBinding>,
        item: CoinHistory
    ) {
        holder.binding.apply {
            coinHistory = item
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemCoinHistoryLayoutBinding> =
        ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_coin_history_layout,
                parent,
                false
            )
        )
}