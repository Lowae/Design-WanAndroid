package com.lowe.wanandroid.base.http

import android.app.Application
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.converter.GsonConverterFactory
import com.lowe.wanandroid.base.http.interceptor.cookie.UserCookieJarImpl
import com.lowe.wanandroid.base.http.interceptor.cookie.cache.DefaultCookieMemoryCache
import com.lowe.wanandroid.base.http.interceptor.cookie.cache.DefaultCookiePersistenceCache
import com.lowe.wanandroid.base.http.interceptor.logInterceptor
import com.lowe.wanandroid.di.ApplicationCoroutineScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Retrofit管理类
 */
object RetrofitManager {

    private const val BASE_URL = "https://www.wanandroid.com"

    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 10

    private lateinit var cookieJarImpl: UserCookieJarImpl

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            // 请求过滤器
            .addInterceptor(logInterceptor)
//            //设置缓存配置,缓存最大10M,设置了缓存之后可缓存请求的数据到data/data/包名/cache/net_cache目录中
//            .cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
//            //添加缓存拦截器 可传入缓存天数
//            .addInterceptor(CacheInterceptor(30))
            .cookieJar(cookieJarImpl)
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()


    fun init(application: Application) {
        cookieJarImpl = UserCookieJarImpl(
            DefaultCookieMemoryCache(),
            DefaultCookiePersistenceCache(
                application,
                ApplicationCoroutineScope.providesIOCoroutineScope()
            )
        )
    }

    /**
     * Retrofit相关配置
     */
    fun <T> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        AppLog.d(msg = BASE_URL)
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl ?: BASE_URL)
            .build()
            .create(serviceClass)
    }
}