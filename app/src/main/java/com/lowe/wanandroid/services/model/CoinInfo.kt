package com.lowe.wanandroid.services.model

/**
 * 积分
 */
data class CoinInfo(
    val coinCount: Int = 0,
    val rank: String = "",
    val level: Int = 0,
    val userId: Int = 0,
    val nickname: String = "",
    val username: String = ""
)