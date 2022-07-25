package com.lowe.wanandroid.ui.navigator

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.common.base.IntKeyPagingSource
import com.lowe.common.base.http.adapter.getOrNull
import com.lowe.common.services.BaseService
import com.lowe.common.services.NavigatorService
import javax.inject.Inject

class NavigatorRepository @Inject constructor(private val service: NavigatorService) {

    suspend fun getNavigationList() = service.getNavigationList()

    suspend fun getTreeList() = service.getTreeList()

    suspend fun getTutorialList() = service.getTutorialList()

    suspend fun getTutorialChapterList(id: Int, orderType: Int = 1) =
        service.getTutorialChapterList(id, orderType)

    fun getSeriesDetailList(id: Int, size: Int) =
        Pager(
            PagingConfig(
                pageSize = size,
                initialLoadSize = size,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(
                BaseService.DEFAULT_PAGE_START_NO,
                service = service
            ) { service, page, size ->
                service.getSeriesDetailList(page, id, size).getOrNull()?.datas ?: emptyList()
            }
        }.flow
}