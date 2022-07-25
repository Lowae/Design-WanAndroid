package com.lowe.common.base.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lowe.common.base.ActivityConfigHelper
import com.lowe.common.base.Config
import com.lowe.common.base.http.adapter.getOrNull
import com.lowe.common.services.model.CollectEvent
import com.lowe.common.services.usecase.ArticleCollectUseCase
import com.lowe.common.theme.ThemeViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [Application]生命周期内的[AndroidViewModel]
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application,
    private val articleCollectUseCase: ArticleCollectUseCase,
    private val themeViewModelDelegate: ThemeViewModelDelegate
) : AndroidViewModel(application), ThemeViewModelDelegate by themeViewModelDelegate {
    /**
     * 全局收藏事件
     */
    private val _collectArticleLiveData = MutableLiveData<CollectEvent>()
    val collectArticleEvent: LiveData<CollectEvent> = _collectArticleLiveData

    init {
        viewModelScope.launch {
            themeViewModelDelegate.themeState.collectLatest {
                ActivityConfigHelper.updateConfig(Config.ThemeConfig(it))
            }
        }
    }

    /**
     * 收藏文章
     */
    fun articleCollectAction(event: CollectEvent) {
        this.viewModelScope.launch {
            articleCollectUseCase.articleCollectAction(event).getOrNull() ?: return@launch
            _collectArticleLiveData.value = event
        }
    }
}