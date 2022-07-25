package com.lowe.common.services.model

/**
 * 分享数据
 */
data class ShareBean(
    val coinInfo: CoinInfo,
    val shareArticles: PageResponse<Article>
)