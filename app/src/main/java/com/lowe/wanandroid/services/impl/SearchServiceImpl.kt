package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.SearchService
import javax.inject.Inject

class SearchServiceImpl @Inject constructor() : SearchService {

    private val service by lazy { RetrofitManager.getService(SearchService::class.java) }

    override suspend fun getSearchHotKey() = apiCall { service.getSearchHotKey() }

    override suspend fun queryBySearchKey(page: Int, key: String) =
        apiCall { service.queryBySearchKey(page, key) }
}