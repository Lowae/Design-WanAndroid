package com.lowe.wanandroid.ui.profile.usecase

import com.lowe.wanandroid.services.repository.AccountRepository
import javax.inject.Inject

/**
 * 用户状态信息UseCase
 */
class UserInfoUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getServerUserInfo() = accountRepository.getServerUserInfo()

    /**
     * 用户账号状态StateFlow
     */
    fun accountStatusFlow() = accountRepository.accountStatusFlow

}