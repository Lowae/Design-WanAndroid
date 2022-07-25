package com.lowe.common.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lowe.common.base.http.cookie.CookieCacheHelper
import com.lowe.common.base.http.cookie.UserCookieJarImpl
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
    ) = UserCookieJarImpl(CookieCacheHelper(dataStore, scope, dispatcher))
}