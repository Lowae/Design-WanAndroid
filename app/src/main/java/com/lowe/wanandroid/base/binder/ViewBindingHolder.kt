package com.lowe.wanandroid.base.binder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewBindingHolder<VD : ViewDataBinding>(val binding: VD) :
    RecyclerView.ViewHolder(binding.root)