package com.lowe.wanandroid.base.http.interceptor.cookie.cache

import okhttp3.Cookie

class DefaultCookieMemoryCache : ICookieMemoryCache {

    private val cookies = mutableSetOf<Cookie>()

    override fun saveAll(cookies: Collection<Cookie>) {
        cookies.forEach { cookie ->
            this.cookies.remove(cookie)
            this.cookies.add(cookie)
        }
    }

    override fun removeAll(cookies: Collection<Cookie>) {
        this.cookies.removeAll(cookies.toSet())
    }

    override fun snapshot() = cookies

    override fun clear() {
        cookies.clear()
    }
}