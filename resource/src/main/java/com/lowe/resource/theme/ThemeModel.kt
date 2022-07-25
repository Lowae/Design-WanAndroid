package com.lowe.resource.theme

import androidx.annotation.ColorInt

data class ThemeModel(
    val key: String,
    @ColorInt val primaryColor: Int,
    var isSelected: Boolean = false
)

enum class ThemePrimaryKey(val storageKey: String) {
    DEFAULT("默认"),
    DYNAMIC("壁纸取色"),
    BEGONIA_RED("海棠红"),
    IRIS_BLUE("鸢尾蓝"),
    CARDAMOM_TIP_GREEN("蔻梢绿")
}

fun fromStorageKey(storageKey: String) =
    ThemePrimaryKey.values().firstOrNull { it.storageKey == storageKey }