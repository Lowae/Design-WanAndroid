package com.lowe.wanandroid.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lowe.wanandroid.base.http.ApiException
import com.lowe.wanandroid.services.ApiResponse
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.services.PageResponse
import com.lowe.wanandroid.services.isSuccess

class IntKeyPagingSource<S : BaseService, V : Any>(
    private val service: S,
    private val load: suspend (S, Int) -> ApiResponse<PageResponse<V>>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val page = params.key ?: BaseService.DEFAULT_PAGE_START_NO
        return try {
            val results = load(service, page)
            if (results.isSuccess()) {
                LoadResult.Page(
                    data = results.data.datas,
                    prevKey = if (page == BaseService.DEFAULT_PAGE_START_NO) null else page - 1,
                    nextKey = if (results.data.datas.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(ApiException(results.errorCode, results.errorMsg))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}