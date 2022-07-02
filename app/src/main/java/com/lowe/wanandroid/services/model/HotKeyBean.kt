package com.lowe.wanandroid.services.model

/**
 * 热搜词
 */
data class HotKeyBean(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)