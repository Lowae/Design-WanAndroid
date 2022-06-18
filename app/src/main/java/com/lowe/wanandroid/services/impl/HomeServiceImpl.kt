package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.HomeService
import javax.inject.Inject

class HomeServiceImpl @Inject constructor() : HomeService {

    private val service by lazy { RetrofitManager.getService(HomeService::class.java) }

    override suspend fun getBanner() = apiCall { service.getBanner() }

    override suspend fun getArticleTopList() = apiCall { service.getArticleTopList() }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ) = apiCall { service.getArticlePageList(pageNo, pageSize) }

    override suspend fun getSquarePageList(pageNo: Int, pageSize: Int) =
        apiCall { service.getSquarePageList(pageNo, pageSize) }

    override suspend fun getAnswerPageList(pageNo: Int) =
        apiCall { service.getAnswerPageList(pageNo) }


}