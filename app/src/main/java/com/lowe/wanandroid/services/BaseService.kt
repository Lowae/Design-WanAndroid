package com.lowe.wanandroid.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface BaseService {

    companion object {

        /**
         * 默认初始页数
         */
        const val DEFAULT_PAGE_START_NO = 0

    }

    suspend fun <T> BaseService.apiCall(api: suspend () -> ApiResponse<T>) =
        withContext(Dispatchers.IO) { api() }
}