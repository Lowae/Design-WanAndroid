package com.lowe.wanandroid.ui.dashboard.repository

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.ApiResponse
import com.lowe.wanandroid.services.PageResponse
import com.lowe.wanandroid.services.ProjectService
import com.lowe.wanandroid.services.apiCall
import com.lowe.wanandroid.services.model.Article

object ProjectRepository : ProjectService {

    private val service by lazy { RetrofitManager.getService(ProjectService::class.java) }

    override suspend fun getProjectTitleList() = apiCall { service.getProjectTitleList() }

    override suspend fun getProjectPageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int
    ): ApiResponse<PageResponse<Article>> = apiCall { service.getProjectPageList(pageNo, pageSize, categoryId) }
}