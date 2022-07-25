package com.lowe.wanandroid.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.drawee.backends.pipeline.Fresco
import com.lowe.common.base.app.ApplicationProxy
import com.lowe.common.base.app.CommonApplicationProxy
import com.lowe.common.base.http.DataStoreFactory
import com.lowe.common.base.http.RetrofitManager
import com.lowe.common.base.http.cookie.UserCookieJarImpl
import com.lowe.common.constant.SettingConstants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * App
 */
@HiltAndroidApp
open class BaseApp : Application() {

    @Inject
    lateinit var cookieJarImpl: UserCookieJarImpl

    private val proxies = listOf<ApplicationProxy>(CommonApplicationProxy)

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        proxies.forEach { it.onCreate(this) }
        Fresco.initialize(this)
        DataStoreFactory.init(this)
        RetrofitManager.init(cookieJarImpl)
        AppCompatDelegate.setDefaultNightMode(SettingConstants.preferenceDarkMode)
    }

    override fun onTerminate() {
        super.onTerminate()
        proxies.forEach { it.onTerminate() }
    }
}