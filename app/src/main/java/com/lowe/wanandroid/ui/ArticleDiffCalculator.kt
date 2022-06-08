package com.lowe.wanandroid.ui

import com.lowe.wanandroid.base.SimpleDiffCalculator
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Banners

object ArticleDiffCalculator {

    fun getCommonArticleDiffCalculator(oldList: List<Any>, newList: List<Any>) =
        SimpleDiffCalculator(
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


}