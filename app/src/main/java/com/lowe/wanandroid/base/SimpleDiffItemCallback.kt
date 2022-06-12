package com.lowe.wanandroid.base

import androidx.recyclerview.widget.DiffUtil

class SimpleDiffItemCallback<T>(
    private val areItemSame: (T, T) -> Boolean,
    private val areContentSame: (T, T) -> Boolean,
    private val changePayload: (T, T) -> Any? = { _: T, _: T -> null }
) : DiffUtil.ItemCallback<T>() {

    override fun getChangePayload(oldItem: T, newItem: T) = changePayload(oldItem, newItem)

    override fun areItemsTheSame(oldItem: T, newItem: T) = areItemSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) = areContentSame(oldItem, newItem)
}