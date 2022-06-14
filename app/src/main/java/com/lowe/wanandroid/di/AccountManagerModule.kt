package com.lowe.wanandroid.di

import com.lowe.wanandroid.account.AccountManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AccountManagerModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationScope applicationScope: CoroutineScope) =
        AccountManager(applicationScope)

}