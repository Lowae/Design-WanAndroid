package com.lowe.wanandroid.utils

import android.util.TypedValue
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible

inline fun <T : View> T?.showIf(needShow: Boolean, afterShown: (T) -> Unit) {
    this?.isVisible = needShow
    if (needShow && this != null) afterShown(this)
}

fun View.setDefaultSelectableItemForeground() {
    runCatching {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            typedValue,
            true
        )
        typedValue
    }.getOrNull()?.let { foreground = AppCompatResources.getDrawable(context, it.resourceId) }
}