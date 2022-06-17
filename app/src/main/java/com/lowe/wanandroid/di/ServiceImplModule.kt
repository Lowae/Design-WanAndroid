package com.lowe.wanandroid.di

import com.lowe.wanandroid.services.GroupService
import com.lowe.wanandroid.services.NavigatorService
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.services.impl.GroupServiceImpl
import com.lowe.wanandroid.services.impl.NavigatorServiceImpl
import com.lowe.wanandroid.services.impl.ProfileServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ServiceImplModule {

    @Binds
    abstract fun getNavigatorServiceImpl(impl: NavigatorServiceImpl): NavigatorService

    @Binds
    abstract fun getGroupServiceImpl(impl: GroupServiceImpl): GroupService

    @Binds
    abstract fun getProfileServiceImpl(impl: ProfileServiceImpl): ProfileService

}