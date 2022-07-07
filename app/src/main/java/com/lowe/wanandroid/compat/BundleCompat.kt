package com.lowe.wanandroid.compat

import android.os.Bundle
import android.os.Parcelable
import com.lowe.wanandroid.utils.SDKUtils
import java.io.Serializable

/**
 * Bundle Compat类
 */
object BundleCompat {
    inline fun <reified T : Parcelable> getParcelable(bundle: Bundle?, key: String?) =
        if (SDKUtils.atLeast33()) {
            bundle?.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle?.getParcelable(key)
        }

    inline fun <reified T : Serializable> getSerializable(bundle: Bundle?, key: String?) =
        if (SDKUtils.atLeast33()) {
            bundle?.getSerializable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle?.getSerializable(key) as T?
        }

    inline fun <reified T : Parcelable> getParcelableArrayList(bundle: Bundle?, key: String?) =
        if (SDKUtils.atLeast33()) {
            bundle?.getParcelableArrayList(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle?.getParcelableArrayList(key)
        }
}
