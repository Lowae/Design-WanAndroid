package com.lowe.wanandroid.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lowe.wanandroid.R
import com.lowe.wanandroid.utils.getPrimaryColor

class NoInterceptTouchSwipeRefreshLayout(context: Context, attrs: AttributeSet?) :
    SwipeRefreshLayout(context, attrs) {

    init {
        setColorSchemeColors(context.getPrimaryColor())
        setProgressBackgroundColorSchemeResource(R.color.md_theme_background)
    }

    /**
     * 不应该拦截任何子View事件
     */
    override fun onInterceptTouchEvent(ev: MotionEvent) = false

}