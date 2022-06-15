package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.ProfileService
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor() : ProfileService {

    private val service by lazy { RetrofitManager.getService(ProfileService::class.java) }

    override suspend fun getMyShareList(page: Int) = apiCall { service.getMyShareList(page) }

    override suspend fun getUserShareList(userId: Int, page: Int) =
        apiCall { service.getUserShareList(userId, page) }
}