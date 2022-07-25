package com.lowe.common.services.model

/**
 * 获取过的积分记录
 */
data class CoinHistory(
    var coinCount: Int,
    var date: Long,
    var desc: String,
    var id: Int,
    var type: Int,
    var reason: String,
    var userId: Int,
    var userName: String
)