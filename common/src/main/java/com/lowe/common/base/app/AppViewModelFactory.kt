package com.lowe.common.base.app

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.lowe.common.services.usecase.ArticleCollectUseCase
import com.lowe.common.theme.ThemeViewModelDelegate
import javax.inject.Inject

/**
 * 用于创建[AppViewModel]实例
 */
class AppViewModelFactory @Inject constructor(
    private val application: Application,
    private val articleCollectUseCase: ArticleCollectUseCase,
    private val themeViewModelDelegate: ThemeViewModelDelegate
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when (modelClass) {
            AppViewModel::class.java -> AppViewModel(application, articleCollectUseCase, themeViewModelDelegate)
            else -> throw IllegalArgumentException("Unknown class $modelClass")
        } as T
    }
}