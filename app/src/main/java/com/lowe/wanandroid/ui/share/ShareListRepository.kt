package com.lowe.wanandroid.ui.share

import com.lowe.wanandroid.services.ProfileService
import javax.inject.Inject

class ShareListRepository @Inject constructor(
    private val profileService: ProfileService
) {

    suspend fun getShareList(page: Int) = profileService.getMyShareList(page)

}