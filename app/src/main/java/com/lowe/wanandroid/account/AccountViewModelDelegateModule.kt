package com.lowe.wanandroid.account

import com.lowe.wanandroid.services.AccountService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AccountViewModelDelegateModule {

    @Singleton
    @Provides
    fun provideAccountViewModelDelegate(
        service: AccountService,
        accountManager: AccountManager
    ): IAccountViewModelDelegate {
        return AccountViewModelDelegate(service, accountManager)
    }
}