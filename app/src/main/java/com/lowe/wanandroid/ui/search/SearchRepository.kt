package com.lowe.wanandroid.ui.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.SearchService
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.ui.BaseViewModel
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchService: SearchService) {

    suspend fun getHotKeyList() = searchService.getSearchHotKey()

    fun search(keywords: String) =
        Pager(
            PagingConfig(
                pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
                initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(service = searchService) { service, page, _ ->
                service.queryBySearchKey(page, keywords).run {
                    if (isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                    this to this.data.datas
                }
            }
        }.flow

}