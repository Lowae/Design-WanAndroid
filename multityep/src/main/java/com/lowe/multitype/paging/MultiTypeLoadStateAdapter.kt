package com.lowe.multitype.paging

import androidx.paging.LoadState

open class MultiTypeLoadStateAdapter : MultiTypeBaseAdapter() {

    var loadState: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
        set(loadState) {
            if (field != loadState) {
                val oldItem = displayLoadStateAsItem(field)
                val newItem = displayLoadStateAsItem(loadState)

                if (oldItem && !newItem) {
                    notifyItemRemoved(0)
                } else if (newItem && !oldItem) {
                    notifyItemInserted(0)
                } else if (oldItem && newItem) {
                    notifyItemChanged(0)
                }
                field = loadState
            }
        }

    override fun getItem(position: Int) = loadState

    override fun getItemCount(): Int = if (displayLoadStateAsItem(loadState)) 1 else 0

    open fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }

}