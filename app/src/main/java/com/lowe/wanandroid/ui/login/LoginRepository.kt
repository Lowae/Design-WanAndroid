package com.lowe.wanandroid.ui.login

import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.services.ApiResponse
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.services.model.User
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val accountManager: AccountManager,
    private val service: ProfileService
) {

    suspend fun login(userInfo: LocalUserInfo): ApiResponse<User> {
        val result: ApiResponse<User> =
            service.login(userInfo.username, userInfo.password)
        if (result.isSuccess()) {
            accountManager.cacheLocalUser(userInfo)
            val baseUserInfo = service.getUserInfo()
            if (baseUserInfo.isSuccess()){
                accountManager.cacheServeUserInfo(baseUserInfo.data)
            }
        }
        return result
    }

    fun getUserInfo() = accountManager.accountStateFlow.value
}