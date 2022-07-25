package com.lowe.common.services.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.common.base.BaseViewModel
import com.lowe.common.base.IntKeyPagingSource
import com.lowe.common.base.http.adapter.getOrNull
import com.lowe.common.services.BaseService
import com.lowe.common.services.CollectService
import com.lowe.common.services.model.CollectEvent
import javax.inject.Inject

/**
 * 收藏Repository
 */
class CollectRepository @Inject constructor(private val service: CollectService) {

    fun getCollectFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(
            BaseService.DEFAULT_PAGE_START_NO,
            service = service
        ) { profileService, page, _ ->
            profileService.getCollectList(page).getOrNull()?.datas ?: emptyList()
        }
    }.flow

    suspend fun articleCollectAction(event: CollectEvent) =
        service.isCollectArticle(event.isCollected, event.id)
}