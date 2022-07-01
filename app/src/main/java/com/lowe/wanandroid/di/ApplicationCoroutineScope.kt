package com.lowe.wanandroid.di

import com.lowe.wanandroid.base.AppLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IOApplicationScope

@InstallIn(SingletonComponent::class)
@Module
object ApplicationCoroutineScope {

    private const val TAG = "ApplicationCoroutineScope"

    private val defaultApplicationScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                TAG, "DefaultApplicationScope:\n${throwable.message.toString()}", throwable
            )
        })
    }

    private val ioApplicationScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                TAG, "IOApplicationScope:\n${throwable.message.toString()}", throwable
            )
        })
    }

    @Singleton
    @Provides
    @DefaultApplicationScope
    fun providesDefaultCoroutineScope() = defaultApplicationScope

    @Singleton
    @Provides
    @IOApplicationScope
    fun providesIOCoroutineScope() = ioApplicationScope
}