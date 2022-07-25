package com.lowe.common.di

import com.lowe.common.services.*
import com.lowe.common.services.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Service 实现提供者
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class ServiceImplModule {

    @Binds
    @Singleton
    abstract fun getHomeServiceImpl(impl: HomeServiceImpl): HomeService

    @Binds
    @Singleton
    abstract fun getProjectServiceImpl(impl: ProjectServiceImpl): ProjectService

    @Binds
    @Singleton
    abstract fun getNavigatorServiceImpl(impl: NavigatorServiceImpl): NavigatorService

    @Binds
    @Singleton
    abstract fun getGroupServiceImpl(impl: GroupServiceImpl): GroupService

    @Binds
    @Singleton
    abstract fun getProfileServiceImpl(impl: ProfileServiceImpl): ProfileService

    @Binds
    @Singleton
    abstract fun getSearchServiceImpl(impl: SearchServiceImpl): SearchService

    @Binds
    @Singleton
    abstract fun getCollectServiceImpl(impl: CollectServiceImpl): CollectService

    @Binds
    @Singleton
    abstract fun getAccountServiceImpl(impl: AccountServiceImpl): AccountService

    @Binds
    @Singleton
    abstract fun getCoinServiceImpl(impl: CoinServiceImpl): CoinService

}