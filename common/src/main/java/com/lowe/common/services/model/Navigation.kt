package com.lowe.common.services.model

/**
 * 导航
 */
data class Navigation(
    var articles: List<Article>,
    var cid: Int,
    var name: String
)