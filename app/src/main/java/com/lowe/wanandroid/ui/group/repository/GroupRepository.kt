package com.lowe.wanandroid.ui.group.repository

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.GroupService
import com.lowe.wanandroid.services.apiCall

object GroupRepository : GroupService {

    private val service by lazy { RetrofitManager.getService(GroupService::class.java) }

    override suspend fun getAuthorTitleList() = apiCall { service.getAuthorTitleList() }

    override suspend fun getAuthorArticles(
        id: Int,
        page: Int,
        pageSize: Int
    ) = apiCall { service.getAuthorArticles(id, page, pageSize) }

}