package com.lowe.wanandroid.services.repository

import com.lowe.wanandroid.account.AccountState
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.services.AccountService
import com.lowe.wanandroid.services.ApiResponse
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.services.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(private val service: AccountService) {

    private val _accountStatusFlow: MutableStateFlow<AccountState> = MutableStateFlow(
        if (isLogin()) AccountState.LogIn(true) else AccountState.LogOut
    )
    val accountStatusFlow: StateFlow<AccountState> = _accountStatusFlow

    private fun isLogin() = service.isLogin()

    suspend fun login(userInfo: LocalUserInfo): ApiResponse<User> {
        val result = service.login(userInfo.username, userInfo.password)
        if (result.isSuccess()) {
            _accountStatusFlow.emit(AccountState.LogIn(false, result.data))
        }
        return result
    }

    suspend fun getServerUserInfo() = service.getUserInfo()

}