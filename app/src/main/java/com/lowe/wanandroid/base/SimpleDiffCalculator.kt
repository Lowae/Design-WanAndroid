package com.lowe.wanandroid.base

import androidx.recyclerview.widget.DiffUtil

abstract class SimpleDiffCalculator(private val oldList: List<Any>, private val newList: List<Any>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return areItemsSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return atrContentsSame(oldItem, newItem)
    }

    abstract fun areItemsSame(oldItem: Any, newItem: Any): Boolean

    abstract fun atrContentsSame(oldItem: Any, newItem: Any): Boolean
}