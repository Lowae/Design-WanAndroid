package com.lowe.wanandroid.services.model

/**
 * Tool工具
 */
data class ToolBean(
    val desc: String = "",
    val icon: String = "",
    val id: Int = 0,
    val isNew: Int = 0,
    val link: String = "",
    val name: String = "",
    val order: Int = 0,
    val showInTab: Int = 0,
    val tabName: String = "",
    val visible: Int = 1
) {
    fun getIconUrl() = "https://www.wanandroid.com/resources/image/pc/tools/$icon"
}