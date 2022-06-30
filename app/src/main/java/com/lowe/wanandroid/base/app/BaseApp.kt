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
import com.lowe.wanandroid.constant.SettingConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BaseApp : Application(), ViewModelStoreOwner {

    private val _viewModelStore by lazy { ViewModelStore() }

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Fresco.initialize(this)
        DataStoreFactory.init(this)
        RetrofitManager.init(this)
        AppCompatDelegate.setDefaultNightMode(SettingConstants.preferenceDarkMode)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    override fun getViewModelStore() = _viewModelStore
}