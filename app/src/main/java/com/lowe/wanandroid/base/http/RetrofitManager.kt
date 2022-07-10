package com.lowe.wanandroid.base.http

import android.app.Application
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.adapter.ErrorHandler
import com.lowe.wanandroid.base.http.adapter.NetworkResponseAdapterFactory
import com.lowe.wanandroid.base.http.converter.GsonConverterFactory
import com.lowe.wanandroid.base.http.cookie.COOKIE_LOGIN_USER_NAME
import com.lowe.wanandroid.base.http.cookie.COOKIE_LOGIN_USER_TOKEN
import com.lowe.wanandroid.base.http.cookie.UserCookieJarImpl
import com.lowe.wanandroid.base.http.cookie.cache.DefaultCookieMemoryCache
import com.lowe.wanandroid.base.http.cookie.cache.DefaultCookiePersistenceCache
import com.lowe.wanandroid.base.http.interceptor.logInterceptor
import com.lowe.wanandroid.di.ApplicationCoroutineScope
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.utils.showShortToast
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Retrofit管理类
 */
object RetrofitManager {

    const val BASE_URL = "https://www.wanandroid.com"

    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 10

    private lateinit var cookieJarImpl: UserCookieJarImpl

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .cookieJar(cookieJarImpl)
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()

    private val servicesMap = ConcurrentHashMap<String, BaseService>()

    fun init(application: Application) {
        cookieJarImpl = UserCookieJarImpl(
            DefaultCookieMemoryCache(),
            DefaultCookiePersistenceCache(
                application,
                ApplicationCoroutineScope.providesIOCoroutineScope()
            )
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseService> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        return servicesMap.getOrPut(serviceClass.name) {
            Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(NetworkResponseAdapterFactory(object : ErrorHandler {
                    override fun bizError(code: Int, msg: String) {
                        ApplicationCoroutineScope.providesMainCoroutineScope().launch {
                            msg.showShortToast()
                        }
                    }

                    override fun otherError(throwable: Throwable) {
                        ApplicationCoroutineScope.providesMainCoroutineScope().launch {
                            // todo()
                            "请求失败".showShortToast()
                        }
                        AppLog.e(msg = throwable.message.toString(), exception = throwable)
                    }
                }))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl ?: BASE_URL)
                .build()
                .create(serviceClass)
        } as T
    }

    fun isLoginCookieValid() = cookieJarImpl.checkValid { cache ->
        var isUserNameValid = false
        var isUserTokenValid = false
        cache.forEach {
            if (it.name == COOKIE_LOGIN_USER_NAME) {
                isUserNameValid = it.value.isNotBlank()
            }
            if (it.name == COOKIE_LOGIN_USER_TOKEN) {
                isUserTokenValid = it.value.isNotBlank()
            }
        }
        return@checkValid isUserNameValid && isUserTokenValid
    }
}