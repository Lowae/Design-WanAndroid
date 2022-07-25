package com.lowe.common.base.http.cookie

import android.util.Log
import kotlinx.coroutines.flow.first
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

internal inline val Cookie.key: String
    get() = (if (secure) "https" else "http") + "://" + domain + path + "|" + name

internal fun Cookie.isExpired() = expiresAt < System.currentTimeMillis()

const val COOKIE_LOGIN_USER_NAME = "loginUserName_wanandroid_com"
const val COOKIE_LOGIN_USER_TOKEN = "token_pass_wanandroid_com"

class UserCookieJarImpl(private val cookieCacheHelper: CookieCacheHelper) : CookieJar {

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val (expiredCookies, validCookies) = cookieCacheHelper.snapshot()
            .partition { it.isExpired() }
        Log.d("UserCookieJarImpl", "loadForRequest: url:$url - expired: $expiredCookies - valid: $validCookies")
        cookieCacheHelper.removeAll(expiredCookies)
        return validCookies.filter { it.matches(url) }
    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val (expiredCookies, validCookies) = cookies.partition { it.isExpired() }
        Log.d("UserCookieJarImpl", "saveFromResponse: url:$url - expired: $expiredCookies - valid: $validCookies")
        cookieCacheHelper.removeAll(expiredCookies)
        cookieCacheHelper.saveAll(validCookies.filter { it.persistent })
    }

    fun clear() {
        cookieCacheHelper.clear()
    }

    suspend fun isLoginCookieValid(): Boolean {
        var isUserNameValid = false
        var isUserTokenValid = false
        cookieCacheHelper.getCookieCache().first().forEach {
            if (it.name == COOKIE_LOGIN_USER_NAME) {
                isUserNameValid = it.value.isNotBlank()
            }
            if (it.name == COOKIE_LOGIN_USER_TOKEN) {
                isUserTokenValid = it.value.isNotBlank()
            }
        }
        return isUserNameValid && isUserTokenValid
    }
}