package com.lowe.common.base.http.cookie

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Cookie
import java.io.IOException

class CookieCacheHelper(
    private val cookieDataStore: DataStore<Preferences>,
    private val applicationScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher
) : ICookieCache {

    private val cookieCache = MutableStateFlow((emptyList<Cookie>()))

    init {
        applicationScope.launch(ioDispatcher) {
            cookieDataStore.data
                .catch {
                    if (it is IOException) {
                        emit(emptyPreferences())
                    } else throw it
                }
                .map { preferences ->
                    Log.d("UserCookieJarImpl", "map preferences: ${preferences}")
                    preferences.asMap().values.mapNotNull {
                        if (it is String) Json.decodeFromString(CookieSerializer, it) else null
                    }
                }.collectLatest {
                    cookieCache.value = it
                    Log.d("UserCookieJarImpl", "cookieDataStore: ${cookieCache.value}")
                }
        }
    }

    fun getCookieCache(): StateFlow<List<Cookie>> = cookieCache.asStateFlow()

    override fun snapshot(): List<Cookie> = cookieCache.value

    override fun saveAll(cookies: Collection<Cookie>) {
        // fast-path
        if (cookies.isEmpty()) return
        cookieCache.value = (cookieCache.value + cookies).distinctBy { it.key }
        applicationScope.launch(ioDispatcher) {
            cookieDataStore.edit { preferences ->
                preferences.putAll(
                    *cookies.map {
                        stringPreferencesKey(it.key) to Json.encodeToString(CookieSerializer, it)
                    }.toTypedArray()
                )
            }
        }
    }

    override fun removeAll(cookies: Collection<Cookie>) {
        applicationScope.launch(ioDispatcher) {
            cookieDataStore.edit { preferences ->
                cookies.forEach {
                    preferences.remove(stringPreferencesKey(it.key))
                }
            }
        }
    }

    override fun clear() {
        // fast-path
        cookieCache.value = emptyList()
        applicationScope.launch(ioDispatcher) {
            cookieDataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

}