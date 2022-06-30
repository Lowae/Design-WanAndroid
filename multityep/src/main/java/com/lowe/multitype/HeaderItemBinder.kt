package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView

abstract class HeaderItemBinder<VH : RecyclerView.ViewHolder> : ItemViewBaseDelegate<LoadState.Header, VH>()