package com.lowe.wanandroid.base.http.cookie

import com.lowe.wanandroid.base.http.cookie.cache.ICookieMemoryCache
import com.lowe.wanandroid.base.http.cookie.cache.ICookiePersistenceCache
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

internal inline val Cookie.key: String
    get() = (if (secure) "https" else "http") + "://" + domain + path + "|" + name

internal fun Cookie.isExpired() = expiresAt < System.currentTimeMillis()

const val COOKIE_LOGIN_USER_NAME = "loginUserName_wanandroid_com"
const val COOKIE_LOGIN_USER_TOKEN = "token_pass_wanandroid_com"

class UserCookieJarImpl(
    private val memoryCache: ICookieMemoryCache,
    private val persistenceCache: ICookiePersistenceCache
) : CookieJar {

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

    fun checkValid(action: (Collection<Cookie>) -> Boolean) = action(memoryCache.snapshot())
}