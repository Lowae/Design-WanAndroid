package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView

abstract class FooterStateItemBinder<VH : RecyclerView.ViewHolder> :
    ItemViewBaseDelegate<androidx.paging.LoadState, VH>()