package com.lowe.wanandroid.base.app

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lowe.wanandroid.services.usecase.ArticleCollectUseCase
import javax.inject.Inject

/**
 * Factory to create [AppViewModel]
 */
class AppViewModelFactory @Inject constructor(
    private val application: Application,
    private val articleCollectUseCase: ArticleCollectUseCase
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != AppViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class, must be ${AppViewModel::class.java}")
        }
        return AppViewModel(
            application,
            articleCollectUseCase
        ) as T
    }
}