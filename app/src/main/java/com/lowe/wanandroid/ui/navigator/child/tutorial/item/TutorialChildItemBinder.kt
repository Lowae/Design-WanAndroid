package com.lowe.wanandroid.ui.navigator.child.tutorial.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemNavigatorChildTutorialLayoutBinding
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.ViewBindingHolder

class TutorialChildItemBinder(private val onClick: (Int, Classify) -> Unit) :
    ItemViewBinder<Classify, ViewBindingHolder<ItemNavigatorChildTutorialLayoutBinding>>() {
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
        holder.binding.apply {
            classify = item
            executePendingBindings()
            root.setOnClickListener {
                onClick(holder.bindingAdapterPosition, item)
            }
        }
    }
}