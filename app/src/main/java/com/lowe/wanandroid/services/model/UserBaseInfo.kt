package com.lowe.wanandroid.services.model

import com.lowe.wanandroid.account.AccountManager

/**
 * 个人基本信息
 */
data class UserBaseInfo(
    val coinInfo: CoinInfo = CoinInfo(),
    val collectArticleInfo: CollectArticleInfo = CollectArticleInfo(),
    val userInfo: User = User()
)

fun UserBaseInfo.isMe() = this.userInfo.id == AccountManager.peekUserBaseInfo().userInfo.id