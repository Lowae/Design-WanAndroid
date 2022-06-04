package com.lowe.wanandroid.base.http.interceptor

import com.lowe.wanandroid.BuildConfig
import com.lowe.wanandroid.base.AppLog
import okhttp3.logging.HttpLoggingInterceptor

/**
 * okhttp 日志拦截器
 * @author LTP  2022/3/21
 */
val logInterceptor by lazy { HttpLoggingInterceptor { AppLog.d(msg = it) }.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC) }