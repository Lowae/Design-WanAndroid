package com.lowe.wanandroid.ui.dashboard.repository

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.ProjectService
import com.lowe.wanandroid.services.apiCall

object ProjectRepository : ProjectService {

    private val service by lazy { RetrofitManager.getService(ProjectService::class.java) }

    override suspend fun getProjectTitleList() = apiCall { service.getProjectTitleList() }
}