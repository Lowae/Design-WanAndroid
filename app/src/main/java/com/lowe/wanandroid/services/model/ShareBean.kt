package com.lowe.wanandroid.services.model

import com.lowe.wanandroid.services.PageResponse

/**
 * 分享数据
 */
data class ShareBean(
    val coinInfo: CoinInfo,
    val shareArticles: PageResponse<Article>
)