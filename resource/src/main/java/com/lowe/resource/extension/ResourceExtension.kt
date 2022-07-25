package com.lowe.resource.extension

import android.content.Context
import com.google.android.material.color.MaterialColors
import com.lowe.resource.R

/**
 * 获取Primary Color
 */
fun Context.getPrimaryColor() = MaterialColors.getColor(
    this,
    com.google.android.material.R.attr.colorPrimary,
    getColor(R.color.md_theme_primary)
)