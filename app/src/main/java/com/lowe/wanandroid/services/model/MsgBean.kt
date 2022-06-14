package com.lowe.wanandroid.services.model

/**
 * 消息
 */
data class MsgBean(
    val category: Int,
    val date: Long,
    val fromUser: String,
    val fromUserId: Int,
    val fullLink: String,
    val id: Int,
    val isRead: Int,
    val link: String,
    val message: String,
    val niceDate: String,
    val tag: String,
    val title: String,
    val userId: Int
)