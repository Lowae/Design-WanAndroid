package com.lowe.wanandroid.widgets

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.google.android.material.chip.Chip
import com.lowe.wanandroid.R
import com.lowe.wanandroid.utils.dp
import com.lowe.wanandroid.utils.dpF
import com.lowe.wanandroid.utils.setRippleBackground

object CommonTagChipWidget {

    /**
     * 生成TextView实现的Chip，Material的[Chip]可定制空间太低了
     */
    fun generateTextViewChip(
        context: Context,
        layoutParams: ViewGroup.MarginLayoutParams
    ) = with(TextView(context, null, android.R.attr.textViewStyle)) {
        layoutParams.setMargins(6.dp)
        this.layoutParams = layoutParams
        this.gravity = Gravity.CENTER
        this.setPadding(6.dp)
        this.textSize = 13F
        this.setTextColor(context.getColor(R.color.md_theme_on_surface_variant))
        setRippleBackground(
            GradientDrawable().also {
                it.cornerRadius = 8.dpF
                it.setColor(context.getColor(R.color.secondary_background_container))
            },
            cornerRadius = 8.dpF
        )
        this
    }

    fun generateChip(
        context: Context,
        layoutParams: ViewGroup.MarginLayoutParams
    ) = with(
        Chip(
            context,
            null,
            com.google.android.material.R.style.Widget_MaterialComponents_Chip_Action
        )
    ) {
        layoutParams.setMargins(6.dp)
        this.layoutParams = layoutParams
        this.gravity = Gravity.CENTER
        this.setPadding(6.dp)
        this.setTextColor(context.getColor(R.color.md_theme_on_surface))
        background = GradientDrawable().also {
            it.cornerRadius = 8.dpF
            it.setColor(context.getColor(R.color.secondary_background_container))
        }
        this
    }

}