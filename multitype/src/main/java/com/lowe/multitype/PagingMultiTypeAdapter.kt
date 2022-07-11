package com.lowe.multitype

import androidx.annotation.IntRange
import androidx.lifecycle.Lifecycle
import androidx.paging.*
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

/**
 * [RecyclerView.Adapter] base class for presenting paged data from [PagingData]s in
 * a [RecyclerView].
 * more @see [PagingDataAdapter]
 *
 * @author Lowae
 */
class PagingMultiTypeAdapter<T : Any>(
    diffCallback: DiffUtil.ItemCallback<T>,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    workerDispatcher: CoroutineDispatcher = Dispatchers.Default,
    override val initialCapacity: Int = 0,
    override var types: Types = MutableTypes(initialCapacity)
) : BaseMultiTypeAdapter() {

    private val differ = AsyncPagingDataDiffer(
        diffCallback = diffCallback,
        updateCallback = AdapterListUpdateCallback(this),
        mainDispatcher = mainDispatcher,
        workerDispatcher = workerDispatcher
    )

    override fun setHasStableIds(hasStableIds: Boolean) =
        throw UnsupportedOperationException("Stable ids are unsupported on PagingDataAdapter.")

    override fun getItem(@IntRange(from = 0) position: Int) = differ.getItem(position) as Any

    override fun getItemCount() = differ.itemCount

    @Suppress("UNCHECKED_CAST")
    suspend fun submitData(pagingData: PagingData<*>) =
        differ.submitData(pagingData as PagingData<T>)

    @Suppress("UNCHECKED_CAST")
    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<*>) =
        differ.submitData(lifecycle, pagingData as PagingData<T>)

    fun retry() = differ.retry()

    fun refresh() = differ.refresh()

    fun peek(@IntRange(from = 0) index: Int) = differ.peek(index)

    fun snapshot(): ItemSnapshotList<T> = differ.snapshot()

    val loadStateFlow: Flow<CombinedLoadStates> = differ.loadStateFlow

    val onPagesUpdatedFlow: Flow<Unit> = differ.onPagesUpdatedFlow

    fun addLoadStateListener(listener: (CombinedLoadStates) -> Unit) =
        differ.addLoadStateListener(listener)

    fun removeLoadStateListener(listener: (CombinedLoadStates) -> Unit) =
        differ.removeLoadStateListener(listener)

    fun addOnPagesUpdatedListener(listener: () -> Unit) = differ.addOnPagesUpdatedListener(listener)

    fun removeOnPagesUpdatedListener(listener: () -> Unit) =
        differ.removeOnPagesUpdatedListener(listener)

    fun withLoadStateHeader(
        headerStateAdapter: PagingLoadStateAdapter,
        header: HeaderStateItemBinder<*>
    ): ConcatAdapter {
        headerStateAdapter.types = this.types
        headerStateAdapter.register(header)
        addLoadStateListener { loadStates ->
            headerStateAdapter.loadState = loadStates.prepend
        }
        return ConcatAdapter(concatAdapterConfig(), headerStateAdapter, this)
    }

    fun withLoadStateFooter(
        footerStateAdapter: PagingLoadStateAdapter,
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            // Avoid of shown footer when adapter is empty
            if (itemCount != 0) {
                footerStateAdapter.loadState = loadStates.append
            }
        }
        return ConcatAdapter(concatAdapterConfig(), this, footerStateAdapter)
    }

    fun withLoadStateHeaderAndFooter(
        headerStateAdapter: PagingLoadStateAdapter,
        header: HeaderStateItemBinder<*>,
        footerStateAdapter: PagingLoadStateAdapter,
        footer: FooterStateItemBinder<*>
    ): ConcatAdapter {
        headerStateAdapter.types = this.types
        headerStateAdapter.register(header)
        footerStateAdapter.types = this.types
        footerStateAdapter.register(footer)
        addLoadStateListener { loadStates ->
            headerStateAdapter.loadState = loadStates.prepend
            footerStateAdapter.loadState = loadStates.append
        }
        return ConcatAdapter(concatAdapterConfig(), headerStateAdapter, this, footerStateAdapter)
    }

    private fun concatAdapterConfig() = ConcatAdapter.Config.Builder().setIsolateViewTypes(false)
        .setStableIdMode(ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS).build()
}