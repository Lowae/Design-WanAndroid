package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.GroupService
import javax.inject.Inject

class GroupServiceImpl @Inject constructor() : GroupService {

    private val service by lazy { RetrofitManager.getService(GroupService::class.java) }

    override suspend fun getAuthorTitleList() = service.getAuthorTitleList()

    override suspend fun getAuthorArticles(id: Int, page: Int, pageSize: Int) =
        service.getAuthorArticles(id, page, pageSize)

}