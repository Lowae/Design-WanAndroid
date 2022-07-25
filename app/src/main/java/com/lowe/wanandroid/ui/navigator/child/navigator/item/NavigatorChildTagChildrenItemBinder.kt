package com.lowe.wanandroid.ui.navigator.child.navigator.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lowe.common.services.model.Article
import com.lowe.common.services.model.Navigation
import com.lowe.common.utils.fromHtml
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemNavigatorChildTagChildrenLayoutBinding
import com.lowe.wanandroid.ui.ViewBindingHolder
import com.lowe.wanandroid.widgets.CommonTagChipWidget

class NavigatorChildTagChildrenItemBinder(private val onTagChildrenClick: (Article) -> Unit) :
    ItemViewBinder<Navigation, ViewBindingHolder<ItemNavigatorChildTagChildrenLayoutBinding>>() {
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
        item: Navigation
    ) {
        holder.binding.apply {
            name = item.name
            executePendingBindings()
            tagChildrenLayout.removeAllViews()
            item.articles.forEach { article ->
                val tv = CommonTagChipWidget.generateTextViewChip(
                    this.root.context,
                    ViewGroup.MarginLayoutParams(
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT
                    )
                )
                tv.text = article.title.fromHtml()
                tv.setOnClickListener { onTagChildrenClick(article) }
                tagChildrenLayout.addView(tv)
            }
        }
    }
}