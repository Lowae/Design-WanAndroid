package com.lowe.wanandroid.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class NoInterceptTouchSwipeRefreshLayout(context: Context, attrs: AttributeSet?) :
    SwipeRefreshLayout(context, attrs) {

    /**
     * 不应该拦截任何子View事件
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

}