package com.lowe.wanandroid.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.lowe.multitype.ItemViewBaseDelegate
import com.lowe.wanandroid.R

class LoadingItemDelegate : ItemViewBaseDelegate<LoadState, LoadingItemDelegate.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_loading_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, item: LoadState) {
        holder.itemView.apply {
            findViewById<ProgressBar>(R.id.progress_bar).visibility =
                if (item is LoadState.Loading) View.VISIBLE else View.GONE
            findViewById<Button>(R.id.retry_button).visibility =
                if (item is LoadState.Error) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.error_msg).apply {
                visibility =
                    if (!(item as? LoadState.Error)?.error?.message.isNullOrBlank()) View.VISIBLE else View.GONE
                text = (item as? LoadState.Error)?.error?.message
            }
        }
    }
}