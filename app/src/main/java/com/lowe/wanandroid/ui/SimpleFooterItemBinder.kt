package com.lowe.wanandroid.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import com.lowe.multitype.FooterStateItemBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemFooterLayoutBinding

open class SimpleFooterItemBinder :
    FooterStateItemBinder<ViewBindingHolder<ItemFooterLayoutBinding>>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): ViewBindingHolder<ItemFooterLayoutBinding> =
        ViewBindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_footer_layout,
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemFooterLayoutBinding>,
        state: LoadState
    ) {
        holder.binding.apply {
            if (state is LoadState.Error) {
                errorMsg.text = state.error.localizedMessage
            }
            loadingProgress.isVisible = state is LoadState.Loading
            retryButton.isVisible = state is LoadState.Error
            errorMsg.isVisible = state is LoadState.Error
            endTips.isVisible = state.endOfPaginationReached
        }
    }
}