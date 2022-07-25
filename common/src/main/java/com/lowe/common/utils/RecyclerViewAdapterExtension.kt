package com.lowe.common.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<*>.isEmpty() = itemCount == 0

inline fun CombinedLoadStates.whenError(action: (LoadState.Error) -> Unit) {
    (refresh as? LoadState.Error ?: source.append as? LoadState.Error
    ?: source.prepend as? LoadState.Error
    ?: append as? LoadState.Error
    ?: prepend as? LoadState.Error)?.let(action)
}

inline val CombinedLoadStates.isRefreshing: Boolean
    get() = source.refresh is LoadState.Loading