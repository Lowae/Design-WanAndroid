package com.lowe.common.base.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lowe.common.base.datastore.DataStorePreference.DataStorePreferenceKeys.PREFERENCE_THEME
import com.lowe.resource.theme.ThemeHelper
import com.lowe.resource.theme.ThemePrimaryKey
import com.lowe.resource.theme.fromStorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorage {

    suspend fun applyTheme(themeKey: String)
    val appliedTheme: Flow<ThemePrimaryKey>

}

@Singleton
class DataStorePreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceStorage {

    object DataStorePreferenceKeys {
        val PREFERENCE_THEME = stringPreferencesKey("setting_theme")
    }

    override suspend fun applyTheme(themeKey: String) {
        dataStore.edit {
            it[PREFERENCE_THEME] = themeKey
        }
    }

    override val appliedTheme: Flow<ThemePrimaryKey>
        get() = dataStore.data.filter { it.contains(PREFERENCE_THEME) }
            .map { fromStorageKey(it[PREFERENCE_THEME].orEmpty()) ?: ThemeHelper.defaultThemeKey }

}