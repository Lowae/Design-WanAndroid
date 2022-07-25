package com.lowe.common.services.impl

import com.lowe.common.base.http.RetrofitManager
import com.lowe.common.services.SearchService
import javax.inject.Inject

class SearchServiceImpl @Inject constructor() : SearchService {

    private val service by lazy { RetrofitManager.getService(SearchService::class.java) }

    override suspend fun getSearchHotKey() = service.getSearchHotKey()

    override suspend fun queryBySearchKey(page: Int, key: String) =
        service.queryBySearchKey(page, key)
}