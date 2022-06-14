package com.lowe.wanandroid.ui.message

import com.lowe.wanandroid.services.MessageService
import javax.inject.Inject

class MessageRepository @Inject constructor(private val service: MessageService) {

    suspend fun getReadiedMsgList(page: Int) = service.getReadiedMessageList(page)

    suspend fun getUnreadMsgList(page: Int) = service.getUnReadMessageList(page)

}