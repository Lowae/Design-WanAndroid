package com.lowe.common.services.model

/**
 * 收藏事件
 */
data class CollectEvent(
    val id: Int,
    val link: String,
    val isCollected: Boolean
)