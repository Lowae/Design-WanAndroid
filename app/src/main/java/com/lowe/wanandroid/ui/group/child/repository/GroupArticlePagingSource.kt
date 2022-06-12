package com.lowe.wanandroid.ui.group.child.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.services.GroupService
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseViewModel

class GroupArticlePagingSource(private val id: Int, private val repository: GroupService) :
    PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: BaseService.DEFAULT_PAGE_START_NO
        val response =
            repository.getAuthorArticles(id, page, params.loadSize)
        if (response.isSuccess().not()) {
            return LoadResult.Error(Throwable(response.errorMsg))
        }
        return LoadResult.Page(
            data = response.data.datas,
            prevKey = if (page == BaseService.DEFAULT_PAGE_START_NO) null else page - 1,
            nextKey = page + params.loadSize / BaseViewModel.DEFAULT_PAGE_SIZE
        ).apply {
            Log.d("GroupArticlePagingSource", "LoadResult.Page ${this.nextKey} - ${this.prevKey}")
        }
    }
}