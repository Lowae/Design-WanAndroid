package com.lowe.wanandroid.ui.coin

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.CoinService
import com.lowe.wanandroid.ui.BaseViewModel
import javax.inject.Inject

class CoinRepository @Inject constructor(private val service: CoinService) {

    fun getCoinHistoryFlow() = Pager(
        PagingConfig(
            pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        IntKeyPagingSource(service = service) { service, page, _ ->
            service.getMyCoinList(page).getOrNull()?.datas ?: emptyList()
        }
    }.flow

}