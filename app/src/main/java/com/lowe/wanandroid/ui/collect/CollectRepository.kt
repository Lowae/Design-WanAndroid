package com.lowe.wanandroid.ui.collect

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.CollectService
import com.lowe.wanandroid.services.model.isSuccess
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
        IntKeyPagingSource(service = service) { profileService, page, _ ->
            profileService.getCollectList(page).run {
                if (isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                this to this.data.datas
            }
        }
    }.flow

    suspend fun articleCollectAction(collect: Boolean, id: Int) =
        service.isCollectArticle(collect, id)
}