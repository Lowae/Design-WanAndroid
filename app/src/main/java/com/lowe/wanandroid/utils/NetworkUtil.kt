package com.lowe.wanandroid.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * 网络工具类
 */
object NetworkUtil {
    /**
     * 网络是否可用
     * @return  网络是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo?.isAvailable == true
    }


    /**
     * 是否连接Wifi
     */
    fun isWifi(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo?.type == ConnectivityManager.TYPE_WIFI
    }
}