package com.lowe.wanandroid.base.http.interceptor.cookie

import androidx.datastore.preferences.core.edit
import com.lowe.wanandroid.base.CookiePreference
import com.lowe.wanandroid.base.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class CookieReceiveInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).also { response ->
            val cookieHeader = response.headers("Set-Cookie")
            if (cookieHeader.isNotEmpty()) {
                runBlocking(Dispatchers.IO) {
                    DataStoreManager.dataStore.edit {
                        it[CookiePreference.cookiePreference] = HashSet(cookieHeader)
                    }
                }
            }
        }
    }
}