package com.lowe.wanandroid.ui.group.child.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lowe.wanandroid.services.GroupService
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseViewModel
import kotlinx.coroutines.flow.Flow

class GroupArticleDataSource(private val service: GroupService) {

    fun fetchAuthorArticles(
        id: Int,
        pageSize: Int = BaseViewModel.DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GroupArticlePagingSource(id, service) }
        ).flow
    }
}