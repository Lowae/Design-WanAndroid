package com.lowe.wanandroid.ui.group

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.GroupService
import com.lowe.wanandroid.ui.BaseViewModel
import javax.inject.Inject

class GroupRepository @Inject constructor(private val service: GroupService) {

    suspend fun getAuthorTitleList() = service.getAuthorTitleList()

    fun getAuthorArticles(id: Int) = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = service) { service, page, size ->
            service.getAuthorArticles(id, page, size).getOrNull()?.datas ?: emptyList()
        }
    }.flow

}