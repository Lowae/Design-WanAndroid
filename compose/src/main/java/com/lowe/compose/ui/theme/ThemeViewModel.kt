package com.lowe.compose.ui.theme

import androidx.lifecycle.viewModelScope
import com.lowe.common.base.BaseViewModel
import com.lowe.common.theme.ThemeViewModelDelegate
import com.lowe.compose.ComposeThemeHelper
import com.lowe.resource.theme.ThemeModel
import com.lowe.resource.theme.ThemePrimaryKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(private val themeViewModelDelegate: ThemeViewModelDelegate) :
    BaseViewModel(), ThemeViewModelDelegate by themeViewModelDelegate {

    val themeStateFlow = themeState.distinctUntilChanged { old, new -> old == new }.map {
        ComposeThemeHelper.snapshotThemes(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun applySelectedTheme(theme: ThemeModel) {
        viewModelScope.launch {
            ThemePrimaryKey.values().firstOrNull { it.storageKey == theme.key }?.takeIf {
                it != currentTheme
            }?.let {
                themeViewModelDelegate.applyTheme(it)
            }
        }
    }

}