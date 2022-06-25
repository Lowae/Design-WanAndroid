package com.lowe.wanandroid.base.http.interceptor.cookie.cache

import okhttp3.Cookie

interface ICookiePersistenceCache : ICookieCache{

    override fun snapshot(): Collection<Cookie> = emptyList()

    fun loadAll(result: (Collection<Cookie>) -> Unit)

}