package com.lowe.resource.theme

import com.google.android.material.color.DynamicColors

object ThemeHelper {

    val defaultThemeKey: ThemePrimaryKey
        get() = if (DynamicColors.isDynamicColorAvailable()) ThemePrimaryKey.DYNAMIC else ThemePrimaryKey.DEFAULT

    fun getThemeRes(key: ThemePrimaryKey) = when (key) {
        ThemePrimaryKey.DEFAULT -> com.lowe.resource.R.style.AppTheme
        ThemePrimaryKey.DYNAMIC -> com.lowe.resource.R.style.DynamicColorTheme
        ThemePrimaryKey.BEGONIA_RED -> com.lowe.resource.R.style.AppTheme_Begonia_Red
        ThemePrimaryKey.IRIS_BLUE -> com.lowe.resource.R.style.AppTheme_Iris_Blue
        ThemePrimaryKey.CARDAMOM_TIP_GREEN -> com.lowe.resource.R.style.AppTheme_Cardamom_Tip_Green
    }
}