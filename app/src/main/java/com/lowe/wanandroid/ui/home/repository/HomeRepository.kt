package com.lowe.wanandroid.ui.home.repository

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.HomeService
import com.lowe.wanandroid.services.apiCall

object HomeRepository : HomeService {

    private val service by lazy { RetrofitManager.getService(HomeService::class.java) }

    override suspend fun getBanner() = apiCall { service.getBanner() }

    override suspend fun getArticleTopList() = apiCall { service.getArticleTopList() }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ) = apiCall { service.getArticlePageList(pageNo, pageSize) }


}