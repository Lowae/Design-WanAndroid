package com.lowe.wanandroid.base.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.services.usecase.ArticleCollectUseCase
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [Application]生命周期内的[AndroidViewModel]
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application,
    private val articleCollectUseCase: ArticleCollectUseCase
) : AndroidViewModel(application) {

    /**
     * 全局收藏事件
     */
    private val _collectArticleLiveData = MutableLiveData<CollectEvent>()
    val collectArticleEvent: LiveData<CollectEvent> = _collectArticleLiveData

    /**
     * 收藏文章
     */
    fun articleCollectAction(event: CollectEvent) {
        this.viewModelScope.launch(BaseViewModel.exceptionHandler) {
            articleCollectUseCase.articleCollectAction(event).success() ?: return@launch
            _collectArticleLiveData.value = event
        }
    }
}