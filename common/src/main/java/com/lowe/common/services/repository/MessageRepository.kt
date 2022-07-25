package com.lowe.common.services.repository

import com.lowe.common.services.ProfileService
import javax.inject.Inject

class MessageRepository @Inject constructor(private val profileService: ProfileService) {

    suspend fun getUnreadMessageCount() = profileService.getUnreadMessageCount()
}