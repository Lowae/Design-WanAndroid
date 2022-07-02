package com.lowe.wanandroid.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * [ViewDataBinding] ViewHolder
 */
class ViewBindingHolder<VD : ViewDataBinding>(val binding: VD) :
    RecyclerView.ViewHolder(binding.root)