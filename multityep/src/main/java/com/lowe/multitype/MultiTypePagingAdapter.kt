package com.lowe.multitype

import androidx.annotation.IntRange
import androidx.lifecycle.Lifecycle
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.CombinedLoadStates
import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MultiTypePagingAdapter(
    diffCallback: DiffUtil.ItemCallback<Any>,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    workerDispatcher: CoroutineDispatcher = Dispatchers.Default,
    override val initialCapacity: Int = 0,
    override var types: Types = MutableTypes(initialCapacity)
) : MultiTypeBaseAdapter() {

    private val differ = AsyncPagingDataDiffer(
        diffCallback = diffCallback,
        updateCallback = AdapterListUpdateCallback(this),
        mainDispatcher = mainDispatcher,
        workerDispatcher = workerDispatcher
    )

    override fun setHasStableIds(hasStableIds: Boolean) =
        throw UnsupportedOperationException("Stable ids are unsupported on PagingDataAdapter.")

    override fun getItem(@IntRange(from = 0) position: Int) = differ.getItem(position) as Any

    override fun getRealItemCount() = differ.itemCount

    suspend fun submitData(pagingData: PagingData<*>) {
        @Suppress("UNCHECKED_CAST")
        differ.submitData(pagingData as PagingData<Any>)
    }

    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<*>) {
        @Suppress("UNCHECKED_CAST")
        differ.submitData(lifecycle, pagingData as PagingData<Any>)
    }

    fun retry() = differ.retry()

    fun refresh() = differ.refresh()

    fun peek(@IntRange(from = 0) index: Int) = differ.peek(index)

    fun snapshot(): ItemSnapshotList<Any> = differ.snapshot()

    val loadStateFlow: Flow<CombinedLoadStates> = differ.loadStateFlow

    val onPagesUpdatedFlow: Flow<Unit> = differ.onPagesUpdatedFlow

    fun addLoadStateListener(listener: (CombinedLoadStates) -> Unit) =
        differ.addLoadStateListener(listener)

    fun removeLoadStateListener(listener: (CombinedLoadStates) -> Unit) =
        differ.removeLoadStateListener(listener)

    fun addOnPagesUpdatedListener(listener: () -> Unit) = differ.addOnPagesUpdatedListener(listener)

    fun removeOnPagesUpdatedListener(listener: () -> Unit) =
        differ.removeOnPagesUpdatedListener(listener)
}