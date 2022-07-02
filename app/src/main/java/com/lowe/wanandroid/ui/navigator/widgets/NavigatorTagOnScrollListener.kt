package com.lowe.wanandroid.ui.navigator.widgets

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 滚动联动效果实现
 */
class NavigatorTagOnScrollListener : RecyclerView.OnScrollListener() {

    private val _firstCompletelyVisiblePosChange = MutableStateFlow(0)
    val firstCompletelyVisiblePosChange: StateFlow<Int> = _firstCompletelyVisiblePosChange

    /**
     * 滚动起始原因
     * startState == [RecyclerView.SCROLL_STATE_DRAGGING] 说明是用户操作
     * startState == [RecyclerView.SCROLL_STATE_SETTLING] 说明是smoothScroll等外部调用滚动，不再回调[firstCompletelyVisiblePosChange]
     */
    private var startState = RecyclerView.SCROLL_STATE_IDLE

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (startState == RecyclerView.SCROLL_STATE_IDLE) {
            startState = newState
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            startState = newState
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        /**
         * 意味着非用户操作导致的滚动，忽略
         */
        if (startState == RecyclerView.SCROLL_STATE_SETTLING) return
        (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
            ?.takeIf { it >= 0 }
            ?.also { pos ->
                _firstCompletelyVisiblePosChange.value = pos
            }
    }

}