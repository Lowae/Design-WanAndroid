package com.lowe.wanandroid.ui.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.common.services.model.ToolBean
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemToolListLayoutBinding
import com.lowe.wanandroid.ui.ViewBindingHolder

class ToolItemBinder(private val onClick: (Int, ToolBean) -> Unit) :
    ItemViewBinder<ToolBean, ViewBindingHolder<ItemToolListLayoutBinding>>() {

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemToolListLayoutBinding>,
        item: ToolBean
    ) {
        holder.binding.apply {
            onClickFunc = onClick
            toolBean = item
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemToolListLayoutBinding> =
        ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater, R.layout.item_tool_list_layout, parent, false
            )
        )
}