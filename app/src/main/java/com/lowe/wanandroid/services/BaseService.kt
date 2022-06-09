package com.lowe.wanandroid.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface BaseService {
}

suspend inline fun <T> BaseService.apiCall(crossinline api: suspend () -> ApiResponse<T>) =
    withContext(Dispatchers.IO) { api() }