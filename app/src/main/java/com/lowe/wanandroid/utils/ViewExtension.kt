package com.lowe.wanandroid.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.lowe.wanandroid.R

inline fun <T : View> T?.showIf(needShow: Boolean, afterShown: (T) -> Unit) {
    this?.isVisible = needShow
    if (needShow && this != null) afterShown(this)
}

fun View.setDefaultSelectableItemForeground() {
    runCatching {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless,
            typedValue,
            true
        )
        typedValue
    }.getOrNull()?.let { foreground = AppCompatResources.getDrawable(context, it.resourceId) }
}

fun View.setRippleBackground(
    originBackgroundDrawable: Drawable,
    @ColorInt rippleColor: Int? = null,
    @Px cornerRadius: Float
) {
    val primaryColor = rippleColor ?: runCatching {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        context.getColor(typedValue.resourceId)
    }.getOrNull() ?: context.getColor(R.color.md_theme_primary)
    background = RippleDrawable(
        ColorStateList.valueOf(primaryColor),
        originBackgroundDrawable,
        ShapeDrawable(RoundRectShape(FloatArray(8) { cornerRadius }, null, null))
    )
}