package com.lowe.wanandroid.ui

import com.lowe.common.base.SimpleDiffCallback
import com.lowe.common.base.SimpleDiffItemCallback
import com.lowe.common.services.model.*

object ArticleDiffCalculator {

    fun getCommonDiffCallback(oldList: List<Any>, newList: List<Any>) =
        SimpleDiffCallback(
            oldList,
            newList,
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Navigation && newItem is Navigation -> oldItem.name == newItem.name
                    oldItem is Article && newItem is Article -> oldItem.id == newItem.id
                    oldItem is Series && newItem is Series -> oldItem.id == newItem.id
                    oldItem is Classify && newItem is Classify -> oldItem.id == newItem.id
                    else -> oldItem.javaClass == newItem.javaClass
                }
            },
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Navigation && newItem is Navigation -> oldItem == newItem
                    oldItem is Series && newItem is Series -> oldItem == newItem
                    oldItem is Classify && newItem is Classify -> oldItem == newItem
                    oldItem is Article && newItem is Article -> oldItem == newItem
                    else -> oldItem.javaClass == newItem.javaClass && oldItem == newItem
                }
            }
        )

    fun getCommonDiffItemCallback() =
        SimpleDiffItemCallback(
            areItemSame = { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Article && newItem is Article -> oldItem.id == newItem.id
                    oldItem is Banners && newItem is Banners -> true
                    oldItem is CollectBean && newItem is CollectBean -> oldItem.id == newItem.id
                    oldItem is MsgBean && newItem is MsgBean -> oldItem.id == newItem.id
                    else -> oldItem::class.java == newItem::class.java
                }
            },
            areContentSame = { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Article && newItem is Article -> oldItem == newItem
                    oldItem is Banners && newItem is Banners -> oldItem == newItem
                    oldItem is CollectBean && newItem is CollectBean -> oldItem == newItem && oldItem.collect == newItem.collect
                    oldItem is MsgBean && newItem is MsgBean -> oldItem == newItem
                    else -> oldItem == newItem
                }
            }
        )
}