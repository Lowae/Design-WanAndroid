package com.lowe.common.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lowe.common.services.BaseService

/**
 * PagingSource 通用封装类
 */
class IntKeyPagingSource<S : BaseService, V : Any>(
    private val pageStart: Int = BaseService.DEFAULT_PAGE_START_NO_1,
    private val service: S,
    private val load: suspend (S, Int, Int) -> List<V>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val page = params.key ?: pageStart
        return try {
            val data = load(service, page, params.loadSize)
            LoadResult.Page(
                data = data,
                prevKey = if (page == pageStart) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}