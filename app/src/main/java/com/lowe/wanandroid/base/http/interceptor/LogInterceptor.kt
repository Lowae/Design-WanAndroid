package com.lowe.wanandroid.base.http.interceptor

import com.lowe.resource.BuildConfig
import com.lowe.wanandroid.base.AppLog
import okhttp3.logging.HttpLoggingInterceptor

val logInterceptor by lazy {
    HttpLoggingInterceptor { AppLog.d(msg = it) }.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)
}