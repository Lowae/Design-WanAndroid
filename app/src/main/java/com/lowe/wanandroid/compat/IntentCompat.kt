@file:Suppress("DEPRECATION")

package com.lowe.wanandroid.compat

import android.content.Intent
import android.os.Parcelable
import com.lowe.wanandroid.utils.SDKUtils
import java.io.Serializable

object IntentCompat {
    inline fun <reified T : Parcelable> getParcelableExtra(intent: Intent, name: String) =
        if (SDKUtils.atLeast33()) {
            intent.getParcelableExtra(name, T::class.java)
        } else {
            intent.getParcelableExtra(name)
        }

    inline fun <reified T : Parcelable> getParcelableArrayListExtra(intent: Intent, name: String) =
        if (SDKUtils.atLeast33()) {
            intent.getParcelableArrayListExtra(name, T::class.java)
        } else {
            intent.getParcelableArrayListExtra(name)
        }

    inline fun <reified T : Serializable> getSerializableExtra(intent: Intent, name: String) =
        if (SDKUtils.atLeast33()) {
            intent.getSerializableExtra(name, T::class.java)
        } else {
            intent.getSerializableExtra(name) as T?
        }
}
