package com.lowe.wanandroid.base.http

import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.adapter.ErrorHandler
import com.lowe.wanandroid.base.http.adapter.NetworkResponseAdapterFactory
import com.lowe.wanandroid.base.http.converter.GsonConverterFactory
import com.lowe.wanandroid.base.http.cookie.UserCookieJarImpl
import com.lowe.wanandroid.base.http.interceptor.logInterceptor
import com.lowe.wanandroid.di.ApplicationCoroutineScope
import com.lowe.wanandroid.di.CoroutinesModule
import com.lowe.wanandroid.services.BaseService
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
    private val errorHandlers = mutableListOf<ErrorHandler>(ErrorToastHandler)

    fun init(cookieJar: UserCookieJarImpl) {
        cookieJarImpl = cookieJar
    }

    fun addErrorHandlerListener(handler: ErrorHandler) {
        errorHandlers.add(handler)
    }

    /**
     * Todo(Inject Implementation)
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : BaseService> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        return servicesMap.getOrPut(serviceClass.name) {
            Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(NetworkResponseAdapterFactory(object : ErrorHandler {
                    override fun bizError(code: Int, msg: String) {
                        ApplicationCoroutineScope.provideApplicationScope()
                            .launch(CoroutinesModule.providesMainImmediateDispatcher()) {
                                errorHandlers.forEach { it.bizError(code, msg) }
                            }
                        AppLog.d(msg = "bizError: code:$code - msg: $msg")
                    }

                    override fun otherError(throwable: Throwable) {
                        ApplicationCoroutineScope.provideApplicationScope()
                            .launch(CoroutinesModule.providesMainImmediateDispatcher()) {
                                errorHandlers.forEach { it.otherError(throwable) }
                            }
                        AppLog.e(msg = throwable.message.toString(), throwable = throwable)
                    }
                }))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl ?: BASE_URL)
                .build()
                .create(serviceClass)
        } as T
    }

}