package com.lowe.wanandroid.base.http

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.lowe.wanandroid.di.ApplicationCoroutineScope

object SearchHistoryPreference {

    private const val KEY_DATA_STORE_SEARCH_HISTORY = "key_data_store_search_history"

    val searchHistoryPreferences = stringSetPreferencesKey(KEY_DATA_STORE_SEARCH_HISTORY)

}

object DataStoreFactory {

    private const val USER_PREFERENCES = "wan_android_preferences"

    private lateinit var defaultDataStore: DataStore<Preferences>

    private val dataStoreMaps = hashMapOf<String, DataStore<Preferences>>()

    fun init(appContext: Context) {
        getDefaultPreferencesDataStore(appContext)
    }

    private fun getDefaultPreferencesDataStore(appContext: Context): DataStore<Preferences> {
        if (this::defaultDataStore.isInitialized.not()) {
            defaultDataStore = createPreferencesDataStore(appContext, USER_PREFERENCES)
        }
        return defaultDataStore
    }

    fun getDefaultPreferencesDataStore() = defaultDataStore

    fun getPreferencesDataStore(appContext: Context, name: String) =
        dataStoreMaps.getOrPut(name) {
            createPreferencesDataStore(appContext, name)
        }

    private fun createPreferencesDataStore(
        appContext: Context,
        name: String
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                    appContext,
                    name
                )
            ),
            scope = ApplicationCoroutineScope.providesIOCoroutineScope(),
            produceFile = { appContext.preferencesDataStoreFile(name) }
        )
    }

}