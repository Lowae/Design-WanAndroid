package com.lowe.wanandroid.services.repository

import com.lowe.wanandroid.services.ProfileService
import javax.inject.Inject

class MessageRepository @Inject constructor(private val profileService: ProfileService) {

    suspend fun getUnreadMessageCount() = profileService.getUnreadMessageCount()
}