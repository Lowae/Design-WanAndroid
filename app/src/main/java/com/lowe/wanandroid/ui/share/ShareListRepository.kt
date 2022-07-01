package com.lowe.wanandroid.ui.share

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.services.model.isSuccess
import com.lowe.wanandroid.services.model.ShareBean
import com.lowe.wanandroid.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ShareListRepository @Inject constructor(
    private val profileService: ProfileService
) {

    private val _shareBeanFlow = MutableSharedFlow<ShareBean>(1)
    val shareBeanFlow: SharedFlow<ShareBean> = _shareBeanFlow

    fun getShareList(userId: String) = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = profileService) { profileService, page, _ ->
            if (AccountManager.isMe(userId)) {
                profileService.getMyShareList(page)
            } else {
                profileService.getUserShareList(userId, page)
            }.run {
                if (this.isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                _shareBeanFlow.emit(this.data)
                this to this.data.shareArticles.datas
            }
        }
    }.flow

}