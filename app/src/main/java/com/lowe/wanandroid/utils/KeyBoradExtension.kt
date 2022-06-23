package com.lowe.wanandroid.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.hideSoftKeyboard() {
    val imm = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        ?: return
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}