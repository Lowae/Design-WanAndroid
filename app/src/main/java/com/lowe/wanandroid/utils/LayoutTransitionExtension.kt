package com.lowe.wanandroid.utils

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View

fun LayoutTransition.getDefaultSlideInAnimator(): ObjectAnimator {
    val pvhAppearingTranslation = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, (-100).dpF, 0f)
    val pvhAppearingAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
    return ObjectAnimator.ofPropertyValuesHolder(
        null as Any?,
        pvhAppearingTranslation,
        pvhAppearingAlpha,
    )
}
