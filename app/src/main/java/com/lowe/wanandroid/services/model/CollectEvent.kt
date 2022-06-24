package com.lowe.wanandroid.services.model

/**
 * 收藏事件
 */
data class CollectEvent(
    val id: Int,
    val link: String,
    val isCollected: Boolean
)