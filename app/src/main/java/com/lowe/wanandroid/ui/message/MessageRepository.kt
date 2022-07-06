package com.lowe.wanandroid.ui.message

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.ui.BaseViewModel
import javax.inject.Inject

class MessageRepository @Inject constructor(private val service: ProfileService) {

    /**
     * 已读消息Flow
     */
    fun getReadiedMsgFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = service) { service, page, _ ->
            service.getReadiedMessageList(page).getOrNull()?.datas ?: emptyList()
        }
    }.flow

    /**
     * 未读消息Flow
     */
    fun getUnreadMsgFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = service) { service, page, _ ->
            service.getUnReadMessageList(page).getOrNull()?.datas ?: emptyList()
        }
    }.flow

}