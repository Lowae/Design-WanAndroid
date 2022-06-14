package com.lowe.wanandroid.ui.message.child

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ItemViewDataBindingBinder
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemMessageChildLayoutBinding
import com.lowe.wanandroid.services.model.MsgBean

class MessageTabChildItemBinder(private val onClick: (Int, MsgBean) -> Unit) :
    ItemViewDataBindingBinder<MsgBean, ViewBindingHolder<ItemMessageChildLayoutBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemMessageChildLayoutBinding> = ViewBindingHolder(
        DataBindingUtil.inflate(
            inflater,
            R.layout.item_message_child_layout,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemMessageChildLayoutBinding>,
        item: MsgBean
    ) {
        super.onBindViewHolder(holder, item)
        holder.binding.apply {
            msg = item
            executePendingBindings()
        }
    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)
        onClick(position, adapterItems[position] as MsgBean)
    }
}