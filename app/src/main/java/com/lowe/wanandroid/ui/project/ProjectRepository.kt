package com.lowe.wanandroid.ui.project

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.services.ProjectService
import com.lowe.wanandroid.services.isSuccess
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val service: ProjectService
) {

    suspend fun getProjectTitleList() = service.getProjectTitleList()

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
                service.getProjectPageList(page, size, categoryId).run {
                    if (this.isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                    this to this.data.datas
                }
            }
        }.flow

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
                service.getNewProjectPageList(page, size).run {
                    if (this.isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                    this to this.data.datas
                }
            }
        }.flow
}