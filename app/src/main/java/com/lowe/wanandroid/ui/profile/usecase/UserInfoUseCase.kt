package com.lowe.wanandroid.ui.profile.usecase

import com.lowe.wanandroid.services.repository.AccountRepository
import javax.inject.Inject

class UserInfoUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getServerUserInfo() = accountRepository.getServerUserInfo()

    fun accountStatusFlow() = accountRepository.accountStatusFlow

}