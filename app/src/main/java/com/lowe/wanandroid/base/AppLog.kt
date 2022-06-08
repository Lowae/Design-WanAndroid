package com.lowe.wanandroid.base

import android.util.Log
import com.lowe.wanandroid.BuildConfig

object AppLog {

    private const val DEFAULT_TAG = "WanAndroid"

    /** 是否是调试状态，即是否打印日志 */
    private val isDebug
        get() = BuildConfig.DEBUG

    /**
     * 默认日志tag
     */
    var tag = DEFAULT_TAG

    /**
     * 打印VERBOSE类型的日志
     */
    fun v(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    /**
     * 打印DEBUG类型的日志
     */
    fun d(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    /**
     * 打印INFO类型的日志
     */
    fun i(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    /**
     * 打印WARN类型的日志
     */
    fun w(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    /**
     * 打印ERROR类型的日志
     */
    fun e(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }
}