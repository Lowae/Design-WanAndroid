package com.lowe.multitype

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * This is a compatible version of [PagingItemViewDelegate].
 * @see PagingItemViewDelegate
 * @author Lowae
 */
abstract class PagingItemViewBinder<T, VH : RecyclerView.ViewHolder> : PagingItemViewDelegate<T, VH>() {

  final override fun onCreateViewHolder(context: Context, parent: ViewGroup): VH {
    return onCreateViewHolder(LayoutInflater.from(context), parent)
  }

  abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH
}
