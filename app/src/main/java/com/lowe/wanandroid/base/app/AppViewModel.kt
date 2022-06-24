package com.lowe.wanandroid.base.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.services.CommonService
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application,
    private val commonService: CommonService
) : AndroidViewModel(application) {

    val userLiveData = MutableLiveData<User>()

    private val _collectArticleLiveData = MutableLiveData<CollectEvent>()
    val collectArticleEvent: LiveData<CollectEvent> = _collectArticleLiveData

    fun articleCollectAction(event: CollectEvent) {
        this.viewModelScope.launch(BaseViewModel.exceptionHandler) {
            commonService.isCollectArticle(event.isCollected, event.id).success() ?: return@launch
            _collectArticleLiveData.value = event
        }
    }
}