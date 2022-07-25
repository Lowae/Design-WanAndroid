package com.lowe.wanandroid.ui.search.begin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.lowe.common.services.model.HotKeyBean
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.widgets.CommonTagChipWidget

/**
 * 搜索热词Tag Item
 */
class SearchHotKeyTagItemBinder(private val onTagChildrenClick: (HotKeyBean) -> Unit) :
    ItemViewBinder<HotKeyBean, SearchHotKeyTagItemBinder.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): Holder {
        return Holder(
            CommonTagChipWidget.generateTextViewChip(
                inflater.context,
                FlexboxLayoutManager.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, item: HotKeyBean) {
        (holder.itemView as? TextView)?.apply {
            text = item.name
            setOnClickListener { onTagChildrenClick(item) }
        }
    }
}