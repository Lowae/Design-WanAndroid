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
annotation class ApplicationScope

@InstallIn(SingletonComponent::class)
@Module
object ApplicationCoroutineScope {

    private const val TAG = "ApplicationCoroutineScope"

    @Singleton
    @Provides
    @ApplicationScope
    fun providesCoroutineScope(): CoroutineScope {
        // providing an instance of CoroutineScope
        return CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable ->
            AppLog.e(
                TAG, throwable.message.toString(), throwable
            )
        })
    }
}