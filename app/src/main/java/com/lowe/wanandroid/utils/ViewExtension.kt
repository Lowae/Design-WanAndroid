package com.lowe.wanandroid.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.view.isVisible

inline fun <T : View> T?.showIf(needShow: Boolean, afterShown: (T) -> Unit) {
    this?.isVisible = needShow
    if (needShow && this != null) afterShown(this)
}

/**
 * 设置Ripple背景
 */
fun View.setRippleBackground(
    originBackgroundDrawable: Drawable,
    @ColorInt rippleColor: Int? = null,
    @Px cornerRadius: Float
) {
    val primaryColor = rippleColor ?: context.getPrimaryColor()
    background = RippleDrawable(
        ColorStateList.valueOf(primaryColor),
        originBackgroundDrawable,
        ShapeDrawable(RoundRectShape(FloatArray(8) { cornerRadius }, null, null))
    )
}