package com.lowe.wanandroid.ui.profile

import androidx.annotation.DrawableRes

class ProfileItemBean(
    @DrawableRes
    val iconRes: Int,
    val title: String,
    var badge: Badge = Badge()
)

class Badge(
    val type: BadgeType = BadgeType.NONE,
    val number: Int = 0
) {
    enum class BadgeType {
        NONE,
        DOT,
        NUMBER
    }
}