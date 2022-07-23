package com.lowe.wanandroid.constant

import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.lowe.wanandroid.base.app.BaseApp

/**
 * 设置页常量
 */
object SettingConstants {
    const val PREFERENCE_KEY_NORMAL_CATEGORY_DYNAMIC_COLORS = "normal_dynamic_colors"
    const val PREFERENCE_KEY_NORMAL_CATEGORY_THEME = "normal_theme"
    const val PREFERENCE_KEY_NORMAL_CATEGORY_DARK_MODE = "normal_darkMode"
    private const val DARK_MODE_ON = "on"
    private const val DARK_MODE_OFF = "off"
    private const val DARK_MODE_FOLLOW_SYSTEM = "system"

    const val PREFERENCE_KEY_OTHER_CATEGORY_ABOUT = "other_about"
    const val PREFERENCE_KEY_OTHER_CATEGORY_GITHUB = "other_github"
    const val PREFERENCE_KEY_OTHER_CATEGORY_LOGOUT = "other_logout"

    /**
     * 是否开启深色模式
     */
    @AppCompatDelegate.NightMode
    val preferenceDarkMode: Int
        get() {
            return getNightMode(
                PreferenceManager.getDefaultSharedPreferences(BaseApp.appContext)
                    .getString(PREFERENCE_KEY_NORMAL_CATEGORY_DARK_MODE, DARK_MODE_FOLLOW_SYSTEM)
                    ?: DARK_MODE_FOLLOW_SYSTEM
            )
        }

    fun getNightMode(value: String) =
        when (value) {
            DARK_MODE_ON -> AppCompatDelegate.MODE_NIGHT_YES
            DARK_MODE_OFF -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

    /**
     * 是否开启动态主题色
     */
    val preferenceDynamicColors: Boolean
        get() {
            return PreferenceManager.getDefaultSharedPreferences(BaseApp.appContext)
                .getBoolean(PREFERENCE_KEY_NORMAL_CATEGORY_DYNAMIC_COLORS, true)
        }

}
