package com.lowe.wanandroid.services

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.model.ApiResponse

interface BaseService {

    fun isLogin() = RetrofitManager.isLoginCookieValid()

    companion object {

        /**
         * 默认初始页数
         */
        const val DEFAULT_PAGE_START_NO = 0

        const val DEFAULT_PAGE_START_NO_1 = 1
    }

    suspend fun <T> BaseService.apiCall(api: suspend () -> ApiResponse<T>) = api()
}