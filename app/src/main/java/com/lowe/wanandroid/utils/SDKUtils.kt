package com.lowe.wanandroid.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object SDKUtils {

    @ChecksSdkIntAtLeast(api = 33)
    fun atLeast33() = Build.VERSION.SDK_INT >= 33

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    fun atLeast31() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    @SuppressLint("ObsoleteSdkInt")
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
    fun atLeast23() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

}