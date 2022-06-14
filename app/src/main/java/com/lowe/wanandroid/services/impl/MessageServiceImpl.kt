package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.MessageService
import javax.inject.Inject

class MessageServiceImpl @Inject constructor() : MessageService {

    private val service by lazy { RetrofitManager.getService(MessageService::class.java) }
    override suspend fun getReadiedMessageList(page: Int) =
        apiCall { service.getReadiedMessageList(page) }

    override suspend fun getUnReadMessageList(page: Int) =
        apiCall { service.getUnReadMessageList(page) }
}