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


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
@Deprecated(message = "use provideApplicationScope() instead", replaceWith = ReplaceWith(expression = "@ApplicationScope with @Dispatcher"))
annotation class IoApplicationScope

/**
 * Application周期内的[CoroutineScope]提供者，当需要在页面生命周期之外开启协程时使用
 */
@InstallIn(SingletonComponent::class)
@Module
object ApplicationCoroutineScope {

    /**
     * 默认[Dispatchers.IO]
     */
    private val ioApplicationScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                "IOApplicationScope:\n${throwable.message.toString()}", throwable = throwable
            )
        })
    }

    private val applicationScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            AppLog.e(
                "applicationScope:\n${throwable.message.toString()}", throwable = throwable
            )
        })

    @Singleton
    @Provides
    @ApplicationScope
    fun provideApplicationScope() = applicationScope

    @Singleton
    @Provides
    @IoApplicationScope
    @Deprecated(message = "use provideApplicationScope() instead", replaceWith = ReplaceWith(expression = "@ApplicationScope with @Dispatcher"))
    fun providesIOCoroutineScope() = ioApplicationScope
}