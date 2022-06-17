package com.lowe.wanandroid.ui.collect.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.ui.BaseViewModel
import javax.inject.Inject

class CollectRepository @Inject constructor(private val profileService: ProfileService) {

    fun getCollectFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(profileService) { profileService, page ->
            profileService.getCollectList(page)
        }
    }.flow
}