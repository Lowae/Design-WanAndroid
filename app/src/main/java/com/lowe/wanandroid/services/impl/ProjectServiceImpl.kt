package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.ProjectService
import javax.inject.Inject

class ProjectServiceImpl @Inject constructor() : ProjectService {

    private val service by lazy { RetrofitManager.getService(ProjectService::class.java) }

    override suspend fun getProjectTitleList() = apiCall { service.getProjectTitleList() }

    override suspend fun getProjectPageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int
    ) = apiCall { service.getProjectPageList(pageNo, pageSize, categoryId) }

    override suspend fun getNewProjectPageList(
        pageNo: Int,
        pageSize: Int
    ) = apiCall { service.getNewProjectPageList(pageNo, pageSize) }

}