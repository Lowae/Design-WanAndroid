package com.lowe.common.theme

import com.lowe.common.di.ApplicationScope
import com.lowe.common.result.successOr
import com.lowe.common.services.usecase.setting.ApplyThemeUseCase
import com.lowe.common.services.usecase.setting.ThemeChangeFlowUseCase
import com.lowe.resource.theme.ThemeHelper
import com.lowe.resource.theme.ThemePrimaryKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

interface ThemeViewModelDelegate {

    val themeState: StateFlow<ThemePrimaryKey>

    val currentTheme: ThemePrimaryKey

    suspend fun applyTheme(key: ThemePrimaryKey)

}

class ThemeActivityDelegateImpl @Inject constructor(
    @ApplicationScope applicationScope: CoroutineScope,
    themeChangeFlowUseCase: ThemeChangeFlowUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase
) : ThemeViewModelDelegate {

    override val themeState: StateFlow<ThemePrimaryKey> = themeChangeFlowUseCase(Unit).map {
        it.successOr(ThemeHelper.defaultThemeKey)
    }.stateIn(applicationScope, SharingStarted.Eagerly, ThemeHelper.defaultThemeKey)

    override val currentTheme: ThemePrimaryKey
        get() = themeState.value

    override suspend fun applyTheme(key: ThemePrimaryKey) {
        applyThemeUseCase(key)
    }
}