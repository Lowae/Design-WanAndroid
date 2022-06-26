package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.CollectService
import javax.inject.Inject

class CollectServiceImpl @Inject constructor() : CollectService {

    private val service by lazy { RetrofitManager.getService(CollectService::class.java) }

    override suspend fun getCollectList(page: Int) = apiCall { service.getCollectList(page) }

    override suspend fun collectArticle(id: Int) = apiCall { service.collectArticle(id) }

    override suspend fun unCollectArticle(id: Int) = apiCall { service.unCollectArticle(id) }

}