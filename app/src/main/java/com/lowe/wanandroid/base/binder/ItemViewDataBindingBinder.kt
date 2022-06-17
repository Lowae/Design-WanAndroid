package com.lowe.wanandroid.base.binder

import androidx.annotation.CallSuper
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.BR

abstract class ItemViewDataBindingBinder<T, VH : ViewBindingHolder<*>> :
    ItemViewBinder<T, VH>() {

    @CallSuper
    override fun onBindViewHolder(holder: VH, item: T) {
        holder.binding.setVariable(BR.binder, this)
        holder.binding.setVariable(BR.position, holder.bindingAdapterPosition)
    }

    open fun onItemClick(position: Int = -1) = Unit

}