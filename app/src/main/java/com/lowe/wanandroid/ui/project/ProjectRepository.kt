package com.lowe.wanandroid.ui.project

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.common.base.IntKeyPagingSource
import com.lowe.common.base.http.adapter.getOrNull
import com.lowe.common.services.BaseService
import com.lowe.common.services.ProjectService
import javax.inject.Inject

class ProjectRepository @Inject constructor(private val service: ProjectService) {

    suspend fun getProjectTitleList() = service.getProjectTitleList()

    /**
     * 项目列表Flow
     */
    fun getProjectListFlow(pageSize: Int, categoryId: Int) =
        Pager(
            PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(
                service = service
            ) { service, page, size ->
                service.getProjectPageList(page, size, categoryId).getOrNull()?.datas ?: emptyList()
            }
        }.flow

    /**
     * 最新项目列表Flow
     */
    fun getNewProjectListFlow(pageSize: Int) =
        Pager(
            PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(
                BaseService.DEFAULT_PAGE_START_NO,
                service
            ) { service, page, size ->
                service.getNewProjectPageList(page, size).getOrNull()?.datas ?: emptyList()
            }
        }.flow
}