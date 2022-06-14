package com.lowe.wanandroid.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object DataStoreManager {

    private const val USER_PREFERENCES = "wan_android_preferences"

    lateinit var dataStore: DataStore<Preferences>

    fun init(application: Context){
        dataStore = createPreferencesDataStore(application)
    }

    private fun createPreferencesDataStore(appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}

object CookiePreference {

    private const val KEY_DATA_STORE_COOKIE_NAME = "key_data_store_cookie_name"

    val cookiePreference = stringSetPreferencesKey(KEY_DATA_STORE_COOKIE_NAME)

}