package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.ProfileService
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor() : ProfileService {

    private val service by lazy { RetrofitManager.getService(ProfileService::class.java) }

    override suspend fun login(username: String, password: String) =
        apiCall { service.login(username, password) }

    override suspend fun getUserInfo() = apiCall { service.getUserInfo() }

    override suspend fun getMyShareList(page: Int) = apiCall { service.getMyShareList(page) }

    override suspend fun getUserShareList(userId: Int, page: Int) =
        apiCall { service.getUserShareList(userId, page) }

    override suspend fun getCollectList(page: Int) = apiCall { service.getCollectList(page) }

    override suspend fun getToolList() = apiCall { service.getToolList() }

    override suspend fun getReadiedMessageList(page: Int) =
        apiCall { service.getReadiedMessageList(page) }

    override suspend fun getUnReadMessageList(page: Int) =
        apiCall { service.getUnReadMessageList(page) }
}