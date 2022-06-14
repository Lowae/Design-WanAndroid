package com.lowe.wanandroid.ui.navigator

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.NavigatorService

object NavigatorRepository : NavigatorService {

    private val service by lazy { RetrofitManager.getService(NavigatorService::class.java) }

    override suspend fun getNavigationList() = apiCall { service.getNavigationList() }

    override suspend fun getTreeList() = apiCall { service.getTreeList() }

    override suspend fun getTutorialList() = apiCall { service.getTutorialList() }

    override suspend fun getTutorialChapterList(id: Int, orderType: Int) =
        apiCall { service.getTutorialChapterList(id, orderType) }

    override suspend fun getSeriesDetailList(page: Int, id: Int, size: Int) =
        apiCall { service.getSeriesDetailList(page, id, size) }
}