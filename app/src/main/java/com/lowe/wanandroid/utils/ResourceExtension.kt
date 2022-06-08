package com.lowe.wanandroid.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * sp to float value
 */
inline val Float.spF: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

/**
 * sp to int value
 */
inline val Int.sp: Int
    get() {
        return this.toFloat().sp
    }

/**
 * sp to int value
 */
inline val Float.sp: Int
    get() {
        return spF.toInt()
    }

/**
 * dp to float value
 */
inline val Float.dpF: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

/**
 * dp to float value
 */
inline val Int.dpF: Float
    get() {
        return toFloat().dpF
    }

/**
 * dp to int value
 */
inline val Int.dp: Int
    get() {
        return toFloat().dp
    }

/**
 * dp to int value
 */
inline val Float.dp: Int
    get() {
        return dpF.toInt()
    }
