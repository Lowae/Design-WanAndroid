package com.lowe.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lowe.common.base.datastore.DataStorePreference
import com.lowe.common.base.datastore.PreferenceStorage
import com.lowe.common.base.http.DataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CookieDataStore

/**
 * [DataStore] 提供者
 */
@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDefaultDataStore(): DataStore<Preferences> =
        DataStoreFactory.getDefaultPreferencesDataStore()

    @Singleton
    @Provides
    @CookieDataStore
    fun provideCookieDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        DataStoreFactory.getPreferencesDataStore(
            context,
            DataStoreFactory.Name.DATA_STORE_NAME_COOKIE
        )

    @Singleton
    @Provides
    fun provideDataStorePreference(dataStore: DataStore<Preferences>): PreferenceStorage =
        DataStorePreference(dataStore)
}