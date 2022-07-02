package com.lowe.wanandroid.ui.message.child

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemMessageChildLayoutBinding
import com.lowe.wanandroid.services.model.MsgBean
import com.lowe.wanandroid.ui.ViewBindingHolder

class MessageTabChildItemBinder(private val onClick: (Int, MsgBean) -> Unit) :
    PagingItemViewBinder<MsgBean, ViewBindingHolder<ItemMessageChildLayoutBinding>>() {

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
        holder.binding.apply {
            onClickFunc = onClick
            msg = item
            executePendingBindings()
        }
    }

}