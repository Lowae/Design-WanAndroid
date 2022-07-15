package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.ProfileService
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor() : ProfileService {

    private val service by lazy { RetrofitManager.getService(ProfileService::class.java) }

    override suspend fun getMyShareList(page: Int) = service.getMyShareList(page)

    override suspend fun getUserShareList(userId: String, page: Int) =
        service.getUserShareList(userId, page)

    override suspend fun getToolList() = service.getToolList()

    override suspend fun getReadiedMessageList(page: Int) = service.getReadiedMessageList(page)

    override suspend fun getUnReadMessageList(page: Int) = service.getUnReadMessageList(page)

    override suspend fun getUnreadMessageCount() = service.getUnreadMessageCount()
}