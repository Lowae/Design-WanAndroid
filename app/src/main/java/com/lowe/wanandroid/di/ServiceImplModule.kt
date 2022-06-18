package com.lowe.wanandroid.di

import com.lowe.wanandroid.services.*
import com.lowe.wanandroid.services.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ServiceImplModule {

    @Binds
    abstract fun getHomeServiceImpl(impl: HomeServiceImpl): HomeService

    @Binds
    abstract fun getProjectServiceImpl(impl: ProjectServiceImpl): ProjectService

    @Binds
    abstract fun getNavigatorServiceImpl(impl: NavigatorServiceImpl): NavigatorService

    @Binds
    abstract fun getGroupServiceImpl(impl: GroupServiceImpl): GroupService

    @Binds
    abstract fun getProfileServiceImpl(impl: ProfileServiceImpl): ProfileService

}