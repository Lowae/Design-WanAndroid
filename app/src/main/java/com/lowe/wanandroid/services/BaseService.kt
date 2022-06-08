package com.lowe.wanandroid.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface BaseService {
}

suspend fun <T> BaseService.apiCall(api: suspend () -> ApiResponse<T>) =
    withContext(Dispatchers.IO) { api() }