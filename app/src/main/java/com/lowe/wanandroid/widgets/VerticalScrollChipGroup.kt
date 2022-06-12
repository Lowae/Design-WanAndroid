package com.lowe.wanandroid.widgets

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ScrollView
import androidx.annotation.IdRes
import androidx.annotation.IntDef
import androidx.core.view.get
import androidx.core.view.setPadding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.lowe.wanandroid.utils.dp
import com.lowe.wanandroid.utils.dpF


class VerticalScrollChipGroup @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ScrollView(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val SNAP_TO_TOP = -1
        const val SNAP_TO_BOTTOM = 1
        const val SNAP_TO_CENTER = 0
    }

    private val chipGroup: ChipGroup = ChipGroup(context, attrs, defStyleAttr).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        isSingleLine = false
        isSingleSelection = true
    }

    @IntDef(SNAP_TO_TOP, SNAP_TO_BOTTOM, SNAP_TO_CENTER)
    annotation class ScrollMode

    init {
        super.addView(chipGroup)
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (chipGroup.isSingleSelection) {
                checkedIds.firstOrNull()?.let { checkedId ->
                    val view = findViewById<Chip>(checkedId)
                    val (x, y) = when (scrollMode) {
                        SNAP_TO_TOP -> view.x.toInt() to view.y.toInt()
                        SNAP_TO_BOTTOM -> view.x.toInt() to view.y.toInt() - this.height + view.height
                        SNAP_TO_CENTER -> view.x.toInt() to view.y.toInt() - this.height / 2
                        else -> view.x.toInt() to view.y.toInt() - this.height / 2
                    }
                    smoothScrollTo(x, y)
                }
            }
        }
        this.setPadding(4.dp)
        chipGroup.isSelectionRequired = true
        chipGroup.layoutTransition = LayoutTransition().apply {
            setAnimateParentHierarchy(false)
            setDuration(LayoutTransition.APPEARING, 500)
            setAnimator(
                LayoutTransition.APPEARING, ObjectAnimator.ofPropertyValuesHolder(
                    null as Any?,
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, (-100).dpF, 0f),
                    PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f),
                )
            )
            setInterpolator(LayoutTransition.APPEARING, DecelerateInterpolator())
        }
    }

    private var scrollMode = SNAP_TO_CENTER

    fun setViews(view: List<Chip>) {
        chipGroup.removeAllViewsInLayout()
        view.forEach {
            chipGroup.addView(it)
        }
    }

    fun addOneView(child: View?, index: Int = -1) {
        chipGroup.addView(child, index)
    }

    fun setScrollMode(@ScrollMode mode: Int) {
        scrollMode = mode
    }

    fun getChipGroup() = chipGroup

    fun checkByPosition(position: Int) {
        checkById(getChipGroup()[position].id)
    }

    fun checkById(@IdRes id: Int) {
        getChipGroup().check(id)
    }
}