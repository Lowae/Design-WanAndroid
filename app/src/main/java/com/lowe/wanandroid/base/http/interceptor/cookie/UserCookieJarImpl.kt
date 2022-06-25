package com.lowe.wanandroid.base.http.interceptor.cookie

import com.lowe.wanandroid.base.http.interceptor.cookie.cache.ICookieMemoryCache
import com.lowe.wanandroid.base.http.interceptor.cookie.cache.ICookiePersistenceCache
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

internal inline val Cookie.key: String
    get() = (if (secure) "https" else "http") + "://" + domain + path + "|" + name

internal fun Cookie.isExpired() = expiresAt < System.currentTimeMillis()

class UserCookieJarImpl(private val memoryCache: ICookieMemoryCache, private val persistenceCache: ICookiePersistenceCache) : CookieJar {

    init {
        persistenceCache.loadAll {
            memoryCache.saveAll(it)
        }
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val (expiredCookies, validCookies) = memoryCache.snapshot().partition { it.isExpired() }
        memoryCache.removeAll(expiredCookies)
        persistenceCache.removeAll(expiredCookies)
        return validCookies.filter { it.matches(url) }
    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        memoryCache.saveAll(cookies)
        persistenceCache.saveAll(cookies.filter { it.persistent })
    }
}