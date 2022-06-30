package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView

abstract class FooterItemBinder<VH : RecyclerView.ViewHolder> : ItemViewBaseDelegate<LoadState.Footer, VH>()