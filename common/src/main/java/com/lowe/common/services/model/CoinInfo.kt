package com.lowe.common.services.model

/**
 * 积分信息
 */
data class CoinInfo(
    val coinCount: Int = 0,
    val rank: String = "",
    val level: Int = 0,
    val userId: Int = 0,
    val nickname: String = "",
    val username: String = ""
)