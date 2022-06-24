package com.lowe.wanandroid.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.base.app.AppViewModelFactory
import com.lowe.wanandroid.base.app.BaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelModule {

    @Singleton
    @Provides
    fun provideAppViewModel(
        application: Application,
        factory: AppViewModelFactory
    ) = ViewModelProvider((application as BaseApp).viewModelStore, factory)[AppViewModel::class.java]

}