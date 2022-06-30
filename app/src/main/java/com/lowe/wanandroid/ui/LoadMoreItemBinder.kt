package com.lowe.wanandroid.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lowe.multitype.FooterItemBinder
import com.lowe.multitype.LoadState
import com.lowe.wanandroid.R

class LoadMoreItemBinder : FooterItemBinder<LoadMoreItemBinder.LoadMoreHolder>() {

    class LoadMoreHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): LoadMoreHolder {
        return LoadMoreHolder(
            LayoutInflater.from(context).inflate(R.layout.item_footer_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadMoreHolder, item: LoadState.Footer) {

    }

}