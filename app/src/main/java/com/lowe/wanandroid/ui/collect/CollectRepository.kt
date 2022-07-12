package com.lowe.wanandroid.ui.collect

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.services.CollectService
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.BaseViewModel
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