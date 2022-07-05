package com.lowe.wanandroid.services.repository

import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.account.AccountState
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.account.RegisterInfo
import com.lowe.wanandroid.services.AccountService
import com.lowe.wanandroid.services.model.ApiResponse
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.model.isSuccess
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户账号相关Repository
 */
@Singleton
class AccountRepository @Inject constructor(private val service: AccountService) {

    val accountStatusFlow: StateFlow<AccountState> = AccountManager.accountStateFlow()

    suspend fun login(userInfo: LocalUserInfo): ApiResponse<User> {
        val result = service.login(userInfo.username, userInfo.password)
        if (result.isSuccess()) {
            AccountManager.logIn(result.data)
        }
        return result
    }


    suspend fun logout(): ApiResponse<Any?> {
        return service.logout().also {
            if (it.isSuccess()) {
                AccountManager.logout()
            }
        }
    }

    suspend fun register(registerInfo: RegisterInfo) =
        service.register(registerInfo.username, registerInfo.password, registerInfo.confirmPassowrd)

    suspend fun getServerUserInfo() = service.getUserInfo()

}