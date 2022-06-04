package com.lowe.wanandroid.ui.home.repository

import com.lowe.wanandroid.base.SimpleDiffCalculator
import com.lowe.wanandroid.services.model.Article

class HomeArticleDiffCalculator(oldList: List<Any>, newList: List<Any>) :
    SimpleDiffCalculator(oldList, newList) {

    override fun areItemsSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is Article && newItem is Article -> oldItem.id == newItem.id
            else -> oldItem::class.java == newItem::class.java
        }
    }

    override fun atrContentsSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is Article && newItem is Article -> oldItem == newItem
            else -> oldItem == newItem
        }
    }
}