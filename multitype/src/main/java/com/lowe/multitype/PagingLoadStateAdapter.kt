package com.lowe.multitype

import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.ConcatAdapter

/**
 * Adapter for displaying a RecyclerView item based on [LoadState], such as a loading spinner, or
 * a retry error button.
 * more @see [LoadStateAdapter]
 *
 * @param types must passed base [PagingMultiTypeAdapter.types] to share since set [ConcatAdapter.Config.isolateViewTypes] is FALSE
 *
 * @author Lowae
 */
open class PagingLoadStateAdapter(footer: FooterStateItemBinder<*>, override var types: Types) :
    BaseMultiTypeAdapter() {

    init {
        register(footer)
    }

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
        return loadState is LoadState.Loading || loadState is LoadState.Error || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }
}