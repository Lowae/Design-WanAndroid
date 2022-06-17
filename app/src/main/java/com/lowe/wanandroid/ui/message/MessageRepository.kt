package com.lowe.wanandroid.ui.message

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.ui.BaseViewModel
import javax.inject.Inject

class MessageRepository @Inject constructor(private val service: ProfileService) {

    fun getReadiedMsgFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = service) { service, page ->
            service.getReadiedMessageList(page).run {
                if (this.isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                this to this.data.datas
            }
        }
    }.flow

    fun getUnreadMsgFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = service) { service, page ->
            service.getUnReadMessageList(page).run {
                if (this.isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                this to this.data.datas
            }
        }
    }.flow

}