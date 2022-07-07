package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView

/**
 * Header ItemBinder
 *
 * @author Lowae
 */
abstract class HeaderStateItemBinder<VH : RecyclerView.ViewHolder> :
    ItemViewBaseDelegate<androidx.paging.LoadState, VH>()