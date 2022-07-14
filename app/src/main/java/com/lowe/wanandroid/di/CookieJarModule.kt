package com.lowe.wanandroid.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lowe.wanandroid.base.http.cookie.UserCookieJarImpl
import com.lowe.wanandroid.base.http.cookie.cache.DefaultCookieMemoryCache
import com.lowe.wanandroid.base.http.cookie.cache.DefaultCookiePersistenceCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CookieJarModule {

    @Singleton
    @Provides
    fun provideCookieJar(
        @CookieDataStore dataStore: DataStore<Preferences>,
        @ApplicationScope scope: CoroutineScope,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) = UserCookieJarImpl(
        DefaultCookieMemoryCache(),
        DefaultCookiePersistenceCache(dataStore, scope, dispatcher)
    )

}