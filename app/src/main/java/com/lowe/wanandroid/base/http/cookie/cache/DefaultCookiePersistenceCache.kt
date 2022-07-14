package com.lowe.wanandroid.base.http.cookie.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lowe.wanandroid.base.http.cookie.CookieSerializer
import com.lowe.wanandroid.base.http.cookie.key
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Cookie
import java.io.IOException

class DefaultCookiePersistenceCache(
    private val cookieDataStore: DataStore<Preferences>,
    private val applicationScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher
) : ICookiePersistenceCache {

    private var persistenceCookies: List<Cookie> = emptyList()
    private var result: ((Collection<Cookie>) -> Unit)? = null

    init {
        applicationScope.launch(ioDispatcher) {
            cookieDataStore.data
                .catch {
                    if (it is IOException) {
                        emit(emptyPreferences())
                    } else throw it
                }
                .map { preferences ->
                    preferences.asMap().values.mapNotNull {
                        if (it is String) Json.decodeFromString(CookieSerializer, it) else null
                    }
                }.collectLatest {
                    persistenceCookies = it
                    result?.invoke(it)
                }
        }
    }

    override fun loadAll(result: (Collection<Cookie>) -> Unit) {
        this.result = result
    }

    override fun snapshot() = persistenceCookies.toList()

    override fun saveAll(cookies: Collection<Cookie>) {
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
        persistenceCookies = emptyList()
        applicationScope.launch(ioDispatcher) {
            cookieDataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }
}