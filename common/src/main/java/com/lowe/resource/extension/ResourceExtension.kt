package com.lowe.resource.extension

import android.content.Context
import android.util.TypedValue
import com.lowe.resource.R

/**
 * 获取Primary Color
 */
fun Context.getPrimaryColor() = runCatching {
    TypedValue().let {
        this.theme.resolveAttribute(android.R.attr.colorPrimary, it, true)
        this.getColor(it.resourceId)
    }
}.getOrNull() ?: this.getColor(R.color.md_theme_primary)