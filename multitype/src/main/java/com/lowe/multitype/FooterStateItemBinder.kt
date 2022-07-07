package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView

/**
 * Footer ItemBinder
 *
 * @author Lowae
 */
abstract class FooterStateItemBinder<VH : RecyclerView.ViewHolder> :
    ItemViewBaseDelegate<androidx.paging.LoadState, VH>()