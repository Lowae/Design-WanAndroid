package com.lowe.wanandroid.utils

import android.view.View
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/** The magnitude of rotation while the list is scrolled. */
private const val SCROLL_ROTATION_MAGNITUDE = 0.25f

/** The magnitude of rotation while the list is over-scrolled. */
private const val OVERSCROLL_ROTATION_MAGNITUDE = -10

/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f

/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f

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