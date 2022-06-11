package com.lowe.wanandroid.ui.navigator.child.tutorial.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ItemViewDataBindingBinder
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemNavigatorChildTutorialLayoutBinding
import com.lowe.wanandroid.services.model.Classify

class TutorialChildItemBinder(private val onClick: (Pair<Int, Classify>) -> Unit) :
    ItemViewDataBindingBinder<Classify, ViewBindingHolder<ItemNavigatorChildTutorialLayoutBinding>>() {
    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemNavigatorChildTutorialLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_navigator_child_tutorial_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemNavigatorChildTutorialLayoutBinding>,
        item: Classify
    ) {
        super.onBindViewHolder(holder, item)
        holder.binding.apply {
            classify = item
            executePendingBindings()
        }
    }

    override fun onItemClick(position: Int) {
        position.takeIf { it >= 0 }?.also { onClick(it to adapterItems[it] as Classify) }
    }
}