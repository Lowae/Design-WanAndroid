package com.lowe.wanandroid.base.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.facebook.drawee.backends.pipeline.Fresco
import com.lowe.wanandroid.base.DataStoreManager
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

/**
 * Application基类
 *
 */
@HiltAndroidApp
open class BaseApp : Application(), ViewModelStoreOwner {

    private lateinit var _appViewModelStore: ViewModelStore
    private lateinit var appViewModel: AppViewModel
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        _appViewModelStore = ViewModelStore()
        Fresco.initialize(this)
        DataStoreManager.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mFactory = null
    }

    /** 获取一个全局的ViewModel */
    fun getAppViewModel(): AppViewModel {
        if (this::appViewModel.isInitialized.not()) {
            appViewModel = ViewModelProvider(this, getAppFactory())[AppViewModel::class.java]
        }
        return appViewModel
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun getViewModelStore() = _appViewModelStore
}