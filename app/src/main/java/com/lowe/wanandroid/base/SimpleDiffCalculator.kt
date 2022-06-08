package com.lowe.wanandroid.base

import androidx.recyclerview.widget.DiffUtil

class SimpleDiffCalculator(
    private val oldList: List<Any>,
    private val newList: List<Any>,
    val areItemSame: (Any, Any) -> Boolean,
    val areContentSame: (Any, Any) -> Boolean
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return areItemSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return areContentSame(oldItem, newItem)
    }
}