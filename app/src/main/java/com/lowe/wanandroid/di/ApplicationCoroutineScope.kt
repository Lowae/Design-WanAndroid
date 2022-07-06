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
annotation class MainApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IOApplicationScope

/**
 * Application周期内的[CoroutineScope]提供者，当需要在页面生命周期之外开启协程时使用
 */
@InstallIn(SingletonComponent::class)
@Module
object ApplicationCoroutineScope {

    private const val TAG = "ApplicationCoroutineScope"

    private val mainApplicationScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                TAG, "mainApplicationScope:\n${throwable.message.toString()}", throwable
            )
        })
    }

    /**
     * 默认[Dispatchers.Default]
     */
    private val defaultApplicationScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                TAG, "DefaultApplicationScope:\n${throwable.message.toString()}", throwable
            )
        })
    }

    /**
     * 默认[Dispatchers.IO]
     */
    private val ioApplicationScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                TAG, "IOApplicationScope:\n${throwable.message.toString()}", throwable
            )
        })
    }

    @Singleton
    @Provides
    @MainApplicationScope
    fun providesMainCoroutineScope() = mainApplicationScope

    @Singleton
    @Provides
    @DefaultApplicationScope
    fun providesDefaultCoroutineScope() = defaultApplicationScope

    @Singleton
    @Provides
    @IOApplicationScope
    fun providesIOCoroutineScope() = ioApplicationScope
}