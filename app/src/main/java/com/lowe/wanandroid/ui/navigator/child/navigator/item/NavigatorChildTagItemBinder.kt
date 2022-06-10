package com.lowe.wanandroid.ui.navigator.child.navigator.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.drakeet.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemNavigatorChildTagLayoutBinding
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.ui.navigator.child.navigator.TagSelectedChange

class NavigatorChildTagItemBinder(private val onClick: (Pair<Int, Navigation>) -> Unit) :
    ItemViewBinder<Navigation, ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>>() {

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
        holder.binding.apply {
            navigation = item
            binder = this@NavigatorChildTagItemBinder
            position = holder.bindingAdapterPosition
            onTagSelectedChange(holder, item)
            executePendingBindings()
        }
    }

    fun onItemClick(position: Int) {
        onClick(position to adapterItems[position] as Navigation)
    }

    private fun onTagSelectedChange(
        holder: ViewBindingHolder<ItemNavigatorChildTagLayoutBinding>,
        item: Navigation
    ) {
        holder.itemView.isSelected = item.isSelected
    }
}