package com.lowe.wanandroid.services.model

/**
 * 项目标题
 */
data class ProjectTitle(
    val author: String = "",
    val children: List<Any> = emptyList(),
    val courseId: Int = 0,
    val cover: String = "",
    val desc: String = "",
    val id: Int = 0,
    val lisense: String = "",
    val lisenseLink: String = "",
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)