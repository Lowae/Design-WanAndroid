package com.lowe.wanandroid.utils

import android.view.View
import android.view.ViewConfiguration
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/** The magnitude of rotation while the list is scrolled. */
private const val SCROLL_ROTATION_MAGNITUDE = 0.25f

/** The magnitude of rotation while the list is over-scrolled. */
private const val OVERSCROLL_ROTATION_MAGNITUDE = -10

/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f

/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f

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

fun RecyclerView.setDefaultEffectFactory() {

    edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
        override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {
            val edgeEffect = object : EdgeEffect(recyclerView.context) {

                private fun translationY(view: View) =
                    SpringAnimation(view, SpringAnimation.TRANSLATION_Y)
                        .setSpring(
                            SpringForce()
                                .setFinalPosition(0f)
                                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                                .setStiffness(SpringForce.STIFFNESS_LOW)
                        )

                override fun onPull(deltaDistance: Float) {
                    super.onPull(deltaDistance)
                    handlePull(deltaDistance)
                }

                override fun onPull(deltaDistance: Float, displacement: Float) {
                    super.onPull(deltaDistance, displacement)
                    handlePull(deltaDistance)
                }

                private fun handlePull(deltaDistance: Float) {
                    // This is called on every touch event while the list is scrolled with a finger.
                    // We simply update the view properties without animation.
                    val sign = if (direction == DIRECTION_TOP) 1 else -1
                    val translationXDelta =
                        sign * recyclerView.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                    recyclerView.forEachVisibleHolder { holder: RecyclerView.ViewHolder ->
                        holder.itemView.translationY += translationXDelta
                    }
                }

                override fun onRelease() {
                    super.onRelease()
                    // The finger is lifted. This is when we should start the animations to bring
                    // the view property values back to their resting states.
                    recyclerView.forEachVisibleHolder { holder: RecyclerView.ViewHolder ->
                        translationY(holder.itemView).start()
                    }
                }

                override fun onAbsorb(velocity: Int) {
                    super.onAbsorb(velocity)
                    val sign = if (direction == DIRECTION_TOP) 1 else -1
                    // The list has reached the edge on fling.
                    val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                    recyclerView.forEachVisibleHolder { holder: RecyclerView.ViewHolder ->
                        translationY(holder.itemView).setStartVelocity(translationVelocity).start()
                    }
                }
            }
            return edgeEffect
        }
    }
}

private inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
    action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}

inline fun CombinedLoadStates.whenError(action: (LoadState.Error) -> Unit) {
    (refresh as? LoadState.Error ?: source.append as? LoadState.Error
    ?: source.prepend as? LoadState.Error
    ?: append as? LoadState.Error
    ?: prepend as? LoadState.Error)?.let(action)
}