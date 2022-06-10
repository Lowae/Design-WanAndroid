package com.lowe.wanandroid.ui.navigator.child.navigator.item

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import com.drakeet.multitype.ItemViewBinder
import com.google.android.flexbox.FlexboxLayout
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemNavigatorChildTagChildrenLayoutBinding
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.utils.dp
import com.lowe.wanandroid.utils.dpF

class NavigatorChildTagChildrenItemBinder :
    ItemViewBinder<Navigation, ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding>>() {
    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate<ItemNavigatorChildTagChildrenLayoutBinding?>(
                inflater,
                R.layout.item_navigator_child_tag_children_layout,
                parent,
                false
            ).apply {
                this.flexboxLayout.background = GradientDrawable().also {
                    it.cornerRadius = 16.dpF
                    it.setColor(this.root.context.getColor(R.color.md_theme_light_secondaryContainer))
                }
            }
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding>,
        item: Navigation
    ) {
        holder.binding.apply {
            navigation = item
            executePendingBindings()
            flexboxLayout.removeAllViews()
            item.articles.forEach {
                val tv = generateTagChildrenTextView(
                    this.root.context,
                    FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                tv.text = it.title
                flexboxLayout.addView(tv)
            }
        }
    }

    private fun generateTagChildrenTextView(
        context: Context,
        layoutParams: FlexboxLayout.LayoutParams
    ) = with(TextView(context)) {
        layoutParams.setMargins(4.dp)
        this.layoutParams = layoutParams
        this.setPadding(4.dp)
        this.setTextColor(context.getColor(R.color.md_theme_dark_surfaceVariant))
        background = GradientDrawable().also {
            it.cornerRadius = 6.dpF
        }
        this
    }
}