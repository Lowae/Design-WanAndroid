package com.lowe.wanandroid.ui

import com.lowe.wanandroid.base.SimpleDiffCallback
import com.lowe.wanandroid.base.SimpleDiffItemCallback
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Banners

object ArticleDiffCalculator {

    fun getCommonArticleDiffCalculator(oldList: List<Any>, newList: List<Any>) =
        SimpleDiffCallback(
            oldList,
            newList,
            areItemSame = { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Article && newItem is Article -> oldItem.id == newItem.id
                    oldItem is Banners && newItem is Banners -> true
                    else -> oldItem::class.java == newItem::class.java
                }
            },
            areContentSame = { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Article && newItem is Article -> oldItem == newItem
                    oldItem is Banners && newItem is Banners -> oldItem == newItem
                    else -> oldItem == newItem
                }
            })

    fun <T : Any> getCommonArticleDiffItemCallback() =
        SimpleDiffItemCallback(
            areItemSame = { oldItem: T, newItem: T ->
                when {
                    oldItem is Article && newItem is Article -> oldItem.id == newItem.id
                    oldItem is Banners && newItem is Banners -> true
                    else -> oldItem::class.java == newItem::class.java
                }
            },
            areContentSame = { oldItem: T, newItem: T ->
                when {
                    oldItem is Article && newItem is Article -> oldItem == newItem
                    oldItem is Banners && newItem is Banners -> oldItem == newItem
                    else -> oldItem == newItem
                }
            }
        )
}