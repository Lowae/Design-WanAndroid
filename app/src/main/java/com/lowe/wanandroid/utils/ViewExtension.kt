package com.lowe.wanandroid.utils

import android.view.View
import androidx.core.view.isVisible

inline fun <T : View> T?.showIf(needShow: Boolean, afterShown: (T) -> Unit) {
    this?.isVisible = needShow
    if (needShow && this != null) afterShown(this)
}