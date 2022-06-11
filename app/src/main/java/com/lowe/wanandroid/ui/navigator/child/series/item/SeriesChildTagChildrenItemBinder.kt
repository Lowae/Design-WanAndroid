package com.lowe.wanandroid.ui.navigator.child.series.item

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import com.drakeet.multitype.ItemViewBinder
import com.google.android.flexbox.FlexboxLayout
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemNavigatorChildTagChildrenLayoutBinding
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.model.Series
import com.lowe.wanandroid.utils.dp
import com.lowe.wanandroid.utils.dpF
import com.lowe.wanandroid.utils.setDefaultSelectableItemForeground

class SeriesChildTagChildrenItemBinder (private val onTagChildrenClick: (Classify) -> Unit) :
    ItemViewBinder<Series, ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding>>() {
    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_navigator_child_tag_children_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding>,
        item: Series
    ) {
        holder.binding.apply {
            name = item.name
            executePendingBindings()
            flexboxLayout.removeAllViews()
            item.children.forEach { classfy ->
                val tv = generateTagChildrenTextView(
                    this.root.context,
                    FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                tv.text = classfy.name
                tv.setOnClickListener { onTagChildrenClick(classfy) }
                flexboxLayout.addView(tv)
            }
        }
    }

    private fun generateTagChildrenTextView(
        context: Context,
        layoutParams: FlexboxLayout.LayoutParams
    ) = with(TextView(context)) {
        layoutParams.setMargins(6.dp)
        this.layoutParams = layoutParams
        this.gravity = Gravity.CENTER
        this.setPadding(6.dp)
        this.setTextColor(context.getColor(R.color.md_theme_dark_surfaceVariant))
        background = GradientDrawable().also {
            it.cornerRadius = 8.dpF
            it.setColor(context.getColor(R.color.md_theme_light_secondaryContainer))
        }
        setDefaultSelectableItemForeground()
        this
    }
}