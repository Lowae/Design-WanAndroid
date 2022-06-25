package com.lowe.wanandroid.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lowe.wanandroid.base.http.exception.ApiException
import com.lowe.wanandroid.services.ApiResponse
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.services.isSuccess

class IntKeyPagingSource<S : BaseService, V : Any>(
    private val pageStart: Int = BaseService.DEFAULT_PAGE_START_NO_1,
    private val service: S,
    private val load: suspend (S, Int, Int) -> Pair<ApiResponse<*>, List<V>>
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
            val (response, data) = load(service, page, params.loadSize)
            if (response.isSuccess()) {
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == pageStart) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(ApiException(response.errorCode, response.errorMsg))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}