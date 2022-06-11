package com.lowe.wanandroid.ui.navigator.child.navigator.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ItemViewDataBindingBinder
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemNavigatorChildTagLayoutBinding
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.ui.navigator.child.navigator.TagSelectedChange

class NavigatorChildTagItemBinder(private val onClick: (Pair<Int, Navigation>) -> Unit) :
    ItemViewDataBindingBinder<Navigation, ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>>() {

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
        item: Navigation,
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
        item: Navigation
    ) {
        super.onBindViewHolder(holder, item)
        holder.binding.apply {
            name = item.name
            onTagSelectedChange(holder, item)
            executePendingBindings()
        }
    }

    override fun onItemClick(position: Int) {
        position.takeIf { it >= 0 }?.also { onClick(it to adapterItems[it] as Navigation) }
    }

    private fun onTagSelectedChange(
        holder: ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>,
        item: Navigation
    ) {
        holder.itemView.isSelected = item.isSelected
        holder.binding.tagTv.setTextColor(holder.itemView.context.getColor(if (item.isSelected) R.color.md_theme_dark_onPrimary else R.color.md_theme_light_outline))
    }
}