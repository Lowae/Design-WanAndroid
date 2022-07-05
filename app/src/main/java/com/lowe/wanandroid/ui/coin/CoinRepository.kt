package com.lowe.wanandroid.ui.coin

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.services.CoinService
import com.lowe.wanandroid.services.model.isSuccess
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
            service.getMyCoinList(page).run {
                if (isSuccess().not()) return@IntKeyPagingSource this to emptyList()
                this to this.data.datas
            }
        }
    }.flow

}