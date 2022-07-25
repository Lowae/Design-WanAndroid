package com.lowe.common.base

import androidx.recyclerview.widget.DiffUtil

class SimpleDiffCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>,
    private val areItemSame: (Any, Any) -> Boolean,
    private val areContentSame: (Any, Any) -> Boolean,
    private val getChangePayload: (Any, Any) -> Any? = { _: Any, _: Any -> null }
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areItemSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areContentSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) =
        getChangePayload(oldList[oldItemPosition], newList[newItemPosition])
}