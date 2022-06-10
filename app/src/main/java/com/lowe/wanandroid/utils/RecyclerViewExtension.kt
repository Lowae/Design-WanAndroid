package com.lowe.wanandroid.utils

import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 分页状态
 */
enum class PagingState {
    END, PAGING, OTHERS, UP
}

/**
 * loadmore 回调
 *
 * @param remainCount
 * @param loadFinish
 * @return
 */
fun RecyclerView.loadMore(
    remainCount: Int = 6,
    loadFinish: () -> Boolean,
    loadMore: () -> Unit
) {
    paging(remainCount, loadFinish) {
        if (it == PagingState.PAGING || it == PagingState.END) {
            loadMore()
        }
    }
}

/**
 * 获得TouchSlop
 */
fun View.getTouchSlop(): Int {
    return ViewConfiguration.get(context).scaledTouchSlop
}

fun RecyclerView.paging(
    remainCount: Int = 6,
    loadFinish: () -> Boolean,
    pagingState: ((PagingState) -> Unit)
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy <= getTouchSlop()) return
            if (loadFinish().not()) return

            var realRemainCount = 0
            if (layoutManager is StaggeredGridLayoutManager) {
                (layoutManager as StaggeredGridLayoutManager).let {
                    val last = IntArray(it.spanCount)
                    it.findLastVisibleItemPositions(last)
                    realRemainCount = it.itemCount - last.last()
                }
            }
            if (layoutManager is LinearLayoutManager) {
                (layoutManager as LinearLayoutManager).let {
                    realRemainCount = it.itemCount - it.findLastVisibleItemPosition()
                }
            }
            realRemainCount -= 1

            pagingState(
                when {
                    realRemainCount == 1 -> PagingState.END
                    realRemainCount <= remainCount -> PagingState.PAGING
                    else -> PagingState.OTHERS
                }
            )
        }
    })
}
//
///**
// * 视频feed有向上加载更多需要，加入了向上加载更多的回调
// *
// * @param loadFinish
// * @return
// */
//fun RecyclerView.pagingWithUp(
//    remainCount: Int = 6,
//    loadFinish: () -> Boolean
//): Observable<PagingState> {
//    return scrollEvents()
//        .filter { loadFinish() }
//        .filter { Math.abs(it.dy) > getTouchSlop() }
//        .map {
//            var realRemainCount = 0
//            var firstPosition = -1
//            if (layoutManager is StaggeredGridLayoutManager) {
//                (layoutManager as StaggeredGridLayoutManager).let {
//                    val last = IntArray(it.spanCount)
//                    it.findLastVisibleItemPositions(last)
//                    realRemainCount = it.itemCount - last.last()
//                    firstPosition = it.findFirstCompletelyVisibleItemPositions(null).min()
//                        ?: RecyclerView.NO_POSITION
//                }
//            }
//            if (layoutManager is LinearLayoutManager) {
//                (layoutManager as LinearLayoutManager).let {
//                    realRemainCount = it.itemCount - it.findLastVisibleItemPosition()
//                    firstPosition = it.findFirstVisibleItemPosition()
//                }
//            }
//            when {
//                realRemainCount == 1 -> PagingState.END
//                realRemainCount <= remainCount -> PagingState.PAGING
//                firstPosition == 0 -> PagingState.UP
//                else -> PagingState.OTHERS
//            }
//        }
//}
//
///**
// *  横向加载更多
// *
// * @param remainCount 预加载数量
// * @param loadFinish
// * @return
// */
//fun RecyclerView.pagingHorizontal(
//    remainCount: Int = 6,
//    loadFinish: (Boolean) -> Boolean
//): Observable<PagingState> {
//    return scrollEvents()
//        .filter { abs(it.dx) > getTouchSlop() }
//        .filter { loadFinish(it.dx < 0) }
//        .map {
//            var realRemainCount = 0
//            val isLoadForward = it.dx < 0
//            if (layoutManager is StaggeredGridLayoutManager) {
//                (layoutManager as StaggeredGridLayoutManager).let {
//                    val last = IntArray(it.spanCount)
//                    if (isLoadForward) {
//                        it.findFirstVisibleItemPositions(last)
//                        realRemainCount = last.first()
//                    } else {
//                        it.findLastVisibleItemPositions(last)
//                        realRemainCount = it.itemCount - last.last()
//                    }
//                }
//            }
//            if (layoutManager is LinearLayoutManager) {
//                (layoutManager as LinearLayoutManager).let {
//                    realRemainCount = if (isLoadForward) {
//                        it.findFirstVisibleItemPosition()
//                    } else {
//                        it.itemCount - it.findLastVisibleItemPosition()
//                    }
//                }
//            }
//
//            when {
//                realRemainCount <= remainCount && isLoadForward.not() -> PagingState.PAGING
//                realRemainCount <= remainCount && isLoadForward -> PagingState.UP
//                else -> PagingState.OTHERS
//            }
//        }
//}

fun RecyclerView.smoothSnapToPosition(
    position: Int,
    snapMode: Int = LinearSmoothScroller.SNAP_TO_START
) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference() = snapMode
    }
    smoothScroller.targetPosition = position
    this.layoutManager?.startSmoothScroll(smoothScroller)
}