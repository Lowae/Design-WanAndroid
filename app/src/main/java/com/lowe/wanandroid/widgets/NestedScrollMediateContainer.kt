package com.lowe.wanandroid.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class NestedScrollMediateContainer(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {


    private var eventX = 0f
    private var eventY = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev ?: return super.onInterceptTouchEvent(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                eventX = ev.x
                eventY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - eventX
                val dy = ev.y - eventY
                computeVerticalScrollOffset()
                if (!canScrollVertically(1) || !canScrollVertically(-1))
            }
        }

        return super.onInterceptTouchEvent(ev)

    }

}