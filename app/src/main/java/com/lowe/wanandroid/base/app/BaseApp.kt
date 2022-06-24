package com.lowe.wanandroid.base.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.facebook.drawee.backends.pipeline.Fresco
import com.lowe.wanandroid.base.DataStoreManager
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
        DataStoreManager.init(this)
    }

    override fun getViewModelStore() = _viewModelStore
}