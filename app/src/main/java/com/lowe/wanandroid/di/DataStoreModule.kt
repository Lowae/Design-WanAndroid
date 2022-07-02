package com.lowe.wanandroid.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lowe.wanandroid.base.http.DataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * [DataStore] 提供者
 */
@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDefaultDataStore(): DataStore<Preferences> = DataStoreFactory.getDefaultPreferencesDataStore()
}