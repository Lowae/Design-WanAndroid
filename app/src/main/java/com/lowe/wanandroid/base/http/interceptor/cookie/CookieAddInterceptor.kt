package com.lowe.wanandroid.base.http.interceptor.cookie

import com.lowe.wanandroid.base.CookiePreference
import com.lowe.wanandroid.base.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CookieAddInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        runBlocking {
            DataStoreManager.dataStore.data.firstOrNull()?.get(CookiePreference.cookiePreference)
                ?: setOf()
        }.forEach {
            builder.addHeader("Cookie", it)
        }
        return chain.proceed(builder.build())
    }
}