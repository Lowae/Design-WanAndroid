package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView

abstract class HeaderStateItemBinder<VH : RecyclerView.ViewHolder> :
    ItemViewBaseDelegate<androidx.paging.LoadState, VH>()