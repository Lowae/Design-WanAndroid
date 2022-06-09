package com.lowe.wanandroid.ui.home.child.explore.repository

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.HomeService
import com.lowe.wanandroid.services.apiCall

object ExploreRepository : HomeService {

    private val service by lazy { RetrofitManager.getService(HomeService::class.java) }

    override suspend fun getBanner() = apiCall { service.getBanner() }

    override suspend fun getArticleTopList() = apiCall { service.getArticleTopList() }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ) = apiCall { service.getArticlePageList(pageNo, pageSize) }


}