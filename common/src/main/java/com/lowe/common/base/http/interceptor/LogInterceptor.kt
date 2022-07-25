package com.lowe.common.base.http.interceptor

import com.lowe.common.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

val logInterceptor by lazy {
    HttpLoggingInterceptor { com.lowe.common.base.AppLog.d(msg = it) }.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)
}