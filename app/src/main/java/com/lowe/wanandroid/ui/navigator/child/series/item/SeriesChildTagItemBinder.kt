package com.lowe.wanandroid.ui.navigator.child.series.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ItemViewDataBindingBinder
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemNavigatorChildTagLayoutBinding
import com.lowe.wanandroid.services.model.Series
import com.lowe.wanandroid.ui.navigator.child.navigator.TagSelectedChange

class SeriesChildTagItemBinder(private val onClick: (Pair<Int, Series>) -> Unit) :
    ItemViewDataBindingBinder<Series, ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemNavigatorChildTagLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_navigator_child_tag_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>,
        item: Series,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, item, payloads)
        else {
            val payload = payloads.firstOrNull() ?: super.onBindViewHolder(holder, item, payloads)
            when (payload) {
                is TagSelectedChange -> {
                    onTagSelectedChange(holder, item)
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>,
        item: Series
    ) {
        super.onBindViewHolder(holder, item)
        holder.binding.apply {
            name = item.name
            onTagSelectedChange(holder, item)
            executePendingBindings()
        }
    }

    override fun onItemClick(position: Int) {
        position.takeIf { it >= 0 }?.also { onClick(it to adapterItems[it] as Series) }
    }

    private fun onTagSelectedChange(
        holder: ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>,
        item: Series
    ) {
        holder.itemView.isSelected = item.isSelected
        holder.binding.tagTv.setTextColor(holder.itemView.context.getColor(if (item.isSelected) R.color.md_theme_dark_onPrimary else R.color.md_theme_light_outline))
    }
}