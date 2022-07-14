package com.lowe.wanandroid.base.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.color.DynamicColors
import com.lowe.wanandroid.base.http.DataStoreFactory
import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.base.http.cookie.UserCookieJarImpl
import com.lowe.wanandroid.constant.SettingConstants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * App
 */
@HiltAndroidApp
open class BaseApp : Application(), ViewModelStoreOwner {

    @Inject
    lateinit var cookieJarImpl: UserCookieJarImpl

    private val _viewModelStore by lazy { ViewModelStore() }

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Fresco.initialize(this)
        DataStoreFactory.init(this)
        RetrofitManager.init(cookieJarImpl)
        AppCompatDelegate.setDefaultNightMode(SettingConstants.preferenceDarkMode)
        if (SettingConstants.preferenceDynamicColors) {
            DynamicColors.applyToActivitiesIfAvailable(this)
        }
    }

    override fun getViewModelStore() = _viewModelStore
}