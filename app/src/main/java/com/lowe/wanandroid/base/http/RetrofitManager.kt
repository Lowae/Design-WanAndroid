package com.lowe.wanandroid.base.http

import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.interceptor.cookie.CookieAddInterceptor
import com.lowe.wanandroid.base.http.interceptor.cookie.CookieReceiveInterceptor
import com.lowe.wanandroid.base.http.interceptor.logInterceptor
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit管理类
 */
object RetrofitManager {

    private const val BASE_URL = "https://www.wanandroid.com"

    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 10

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            // 请求过滤器
            .addInterceptor(logInterceptor)
            .addInterceptor(CookieReceiveInterceptor())
            .addInterceptor(CookieAddInterceptor())
//            //设置缓存配置,缓存最大10M,设置了缓存之后可缓存请求的数据到data/data/包名/cache/net_cache目录中
//            .cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
//            //添加缓存拦截器 可传入缓存天数
//            .addInterceptor(CacheInterceptor(30))
            // 请求超时时间
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .cookieJar(CookieJar.NO_COOKIES)
            .build()

    /**
     * Retrofit相关配置
     */
    fun <T> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        AppLog.d(msg = BASE_URL)
        return Retrofit.Builder()
            .client(client)
//            .addConverterFactory(CustomGsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl ?: BASE_URL)
            .build()
            .create(serviceClass)
    }
}