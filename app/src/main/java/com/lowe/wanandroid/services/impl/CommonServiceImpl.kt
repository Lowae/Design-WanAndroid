package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.CommonService
import javax.inject.Inject

class CommonServiceImpl @Inject constructor() : CommonService {

    private val service by lazy { RetrofitManager.getService(CommonService::class.java) }

    override suspend fun login(username: String, password: String) =
        apiCall { service.login(username, password) }

    override suspend fun getUserInfo() = apiCall { service.getUserInfo() }

    override suspend fun collectArticle(id: Int) = apiCall { service.collectArticle(id) }

    override suspend fun unCollectArticle(id: Int) = apiCall { service.unCollectArticle(id) }

}