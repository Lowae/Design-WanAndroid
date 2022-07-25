package com.lowe.wanandroid.ui.coin.ranking.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.common.services.model.CoinInfo
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemCoinInfoLayoutBinding
import com.lowe.wanandroid.ui.ViewBindingHolder

class CoinInfoItemBinder :
    PagingItemViewBinder<CoinInfo, ViewBindingHolder<ItemCoinInfoLayoutBinding>>() {
    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemCoinInfoLayoutBinding>,
        item: CoinInfo
    ) {
        holder.binding.apply {
            coinInfo = item
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemCoinInfoLayoutBinding> =
        ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_coin_info_layout,
                parent,
                false
            )
        )
}