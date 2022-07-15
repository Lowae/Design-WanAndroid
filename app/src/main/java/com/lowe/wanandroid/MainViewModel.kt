package com.lowe.wanandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.message.UnreadMessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(unreadMessageManager: UnreadMessageManager) :
    BaseViewModel() {

    /**
     * 首页NavigationBottomTab双击事件
     */
    private val _mainTabDoubleClickLiveData = MutableLiveData<String>()
    val mainTabDoubleClickLiveData: LiveData<String> = _mainTabDoubleClickLiveData

    fun bottomDoubleClick(tag: String) {
        _mainTabDoubleClickLiveData.value = tag
    }

    val profileUnread = unreadMessageManager.profileUnreadState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )
}