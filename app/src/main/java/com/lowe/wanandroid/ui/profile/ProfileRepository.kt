package com.lowe.wanandroid.ui.profile

import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.services.isSuccess
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val accountManager: AccountManager,
    private val service: ProfileService
) {

    suspend fun getServerUserInfo() {
        service.getUserInfo().also {
            if (it.isSuccess()) {
                accountManager.cacheServeUserInfo(it.data)
            }
        }
    }

    fun getUserBaseInfoStateFlow() = accountManager.userBaseInfoStateFlow

}