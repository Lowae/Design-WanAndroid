@file:Suppress("DEPRECATION")

package com.lowe.wanandroid.compat

import android.content.Intent
import android.os.Parcelable
import com.lowe.wanandroid.utils.SDKUtils
import java.io.Serializable

/**
 * Intent Compat类
 */
object IntentCompat {
    inline fun <reified T : Parcelable> getParcelableExtra(intent: Intent, name: String): T? =
        if (SDKUtils.atLeast33()) {
            intent.getParcelableExtra(name)
        } else {
            intent.getParcelableExtra(name)
        }

    inline fun <reified T : Parcelable> getParcelableArrayListExtra(
        intent: Intent,
        name: String
    ): ArrayList<T>? =
        if (SDKUtils.atLeast33()) {
            intent.getParcelableArrayListExtra(name)
        } else {
            intent.getParcelableArrayListExtra(name)
        }

    inline fun <reified T : Serializable> getSerializableExtra(intent: Intent, name: String): Serializable? =
        if (SDKUtils.atLeast33()) {
            intent.getSerializableExtra(name)
        } else {
            intent.getSerializableExtra(name)
        }
}
