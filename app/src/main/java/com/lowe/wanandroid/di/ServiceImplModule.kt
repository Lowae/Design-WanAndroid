package com.lowe.wanandroid.di

import com.lowe.wanandroid.services.AccountService
import com.lowe.wanandroid.services.impl.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ServiceImplModule {

    @Binds
    abstract fun getServiceImpl(impl: AccountServiceImpl): AccountService

}