package com.lowe.wanandroid.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.facebook.drawee.backends.pipeline.Fresco
import kotlin.properties.Delegates

/**
 * Application基类
 *
 */
open class BaseApp : Application(), ViewModelStoreOwner {

    private lateinit var _appViewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        _appViewModelStore = ViewModelStore()
        Fresco.initialize(this)
    }

    /** 获取一个全局的ViewModel */
    fun getAppViewModelProvider() = ViewModelProvider(this, getAppFactory())

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun getViewModelStore() = _appViewModelStore
}