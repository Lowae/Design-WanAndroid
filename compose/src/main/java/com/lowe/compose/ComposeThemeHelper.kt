package com.lowe.compose

import android.content.Context
import androidx.annotation.ColorInt
import com.lowe.common.base.app.CommonApplicationProxy
import com.lowe.resource.theme.ThemeModel
import com.lowe.resource.theme.ThemePrimaryKey

object ComposeThemeHelper {

    fun getThemeColorScheme(key: ThemePrimaryKey): ChangeableTheme = when (key) {
        ThemePrimaryKey.DEFAULT -> DefaultTheme
        ThemePrimaryKey.DYNAMIC -> DynamicTheme
        ThemePrimaryKey.BEGONIA_RED -> BegoniaTheme
        ThemePrimaryKey.IRIS_BLUE -> IrisTheme
        ThemePrimaryKey.CARDAMOM_TIP_GREEN -> CardamomTipTheme
    }

    fun snapshotThemes(selectedKey: ThemePrimaryKey): List<ThemeModel> =
        ThemePrimaryKey.values().map {
            ThemeModel(
                it.storageKey,
                it.primaryColor(CommonApplicationProxy.application),
                it == selectedKey
            )
        }
}

@ColorInt
fun ThemePrimaryKey.primaryColor(context: Context): Int = when (this) {
    ThemePrimaryKey.DEFAULT -> context.getColor(com.lowe.resource.R.color.seed)
    ThemePrimaryKey.BEGONIA_RED -> context.getColor(com.lowe.resource.R.color.begonia_red_seed)
    ThemePrimaryKey.IRIS_BLUE -> context.getColor(com.lowe.resource.R.color.iris_blue_seed)
    ThemePrimaryKey.CARDAMOM_TIP_GREEN -> context.getColor(com.lowe.resource.R.color.cardamom_tip_green_seed)
    ThemePrimaryKey.DYNAMIC -> context.getColor(android.R.color.system_accent1_600)
}
