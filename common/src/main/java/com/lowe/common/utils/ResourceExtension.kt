package com.lowe.common.utils

import android.content.res.Resources
import android.util.TypedValue

inline val Float.spF: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

inline val Int.sp: Int
    get() {
        return this.toFloat().sp
    }

inline val Float.sp: Int
    get() {
        return spF.toInt()
    }

inline val Float.dpF: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

inline val Int.dpF: Float
    get() {
        return toFloat().dpF
    }

inline val Int.dp: Int
    get() {
        return toFloat().dp
    }

inline val Float.dp: Int
    get() {
        return dpF.toInt()
    }