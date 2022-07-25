package com.lowe.common.account

import com.lowe.common.base.http.adapter.NetworkResponse
import com.lowe.common.base.http.adapter.isSuccess
import com.lowe.common.base.http.adapter.whenSuccess
import com.lowe.common.services.AccountService
import com.lowe.common.services.model.User
import com.lowe.common.services.model.UserBaseInfo
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface IAccountViewModelDelegate {

    val accountState: StateFlow<AccountState>

    val accountInfo: StateFlow<UserBaseInfo>

    val isLogin: Boolean

    val userId: String

    suspend fun fetchUserInfo(): NetworkResponse<UserBaseInfo>

    suspend fun login(localUserInfo: LocalUserInfo): NetworkResponse<User>

    suspend fun logout(): NetworkResponse<Any>

    suspend fun register(registerInfo: RegisterInfo): NetworkResponse<Any>
}

internal class AccountViewModelDelegate @Inject constructor(
    private val service: AccountService,
    private val accountManager: AccountManager
) : IAccountViewModelDelegate {

    override val accountState: StateFlow<AccountState>
        get() = accountManager.accountStateFlow()

    override val accountInfo: StateFlow<UserBaseInfo>
        get() = accountManager.collectUserInfoFlow()

    override val isLogin: Boolean
        get() = accountManager.isLogin()

    override val userId: String
        get() = accountInfo.value.userInfo.id

    override suspend fun fetchUserInfo(): NetworkResponse<UserBaseInfo> {
        return service.getUserInfo().also {
            it.whenSuccess { userBaseInfo ->
                accountManager.cacheUserBaseInfo(userBaseInfo)
            }
        }
    }

    override suspend fun login(localUserInfo: LocalUserInfo): NetworkResponse<User> {
        val result = service.login(localUserInfo.username, localUserInfo.password)
        result.whenSuccess {
            accountManager.logIn(it)
        }
        return result
    }

    override suspend fun logout(): NetworkResponse<Any> {
        return service.logout().also {
            if (it.isSuccess) {
                accountManager.logout()
            }
        }
    }

    override suspend fun register(registerInfo: RegisterInfo): NetworkResponse<Any> {
        return service.register(
            registerInfo.username,
            registerInfo.password,
            registerInfo.confirmPassword
        )
    }
}