package com.lowe.wanandroid.base.http.interceptor

import com.lowe.wanandroid.base.BaseApp.Companion.appContext
import com.lowe.wanandroid.utils.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 缓存拦截器,用于无网情况下传递header直接拉取之前缓存的数据
 * @param day 缓存天数
 *
 * @author LTP 2022/4/14
 */
class CacheInterceptor(private var day: Int = 7) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtil.isNetworkAvailable(appContext)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val response = chain.proceed(request)
        if (!NetworkUtil.isNetworkAvailable(appContext)) {
            val maxAge = 60 * 60
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * day
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return response
    }
}