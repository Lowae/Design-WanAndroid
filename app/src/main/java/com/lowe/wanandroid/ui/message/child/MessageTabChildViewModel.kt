package com.lowe.wanandroid.ui.message.child

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.common.base.BaseViewModel
import com.lowe.wanandroid.ui.message.MessageRepository
import com.lowe.wanandroid.ui.message.UnreadMessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageTabChildViewModel @Inject constructor(
    repository: MessageRepository,
    private val unreadMessageManager: UnreadMessageManager
) :
    BaseViewModel() {

    val getReadiedMsgFlow = repository.getReadiedMsgFlow().cachedIn(viewModelScope)

    val getUnreadMsgFlow = repository.getUnreadMsgFlow().cachedIn(viewModelScope)

    fun clearUnreadMessage() = unreadMessageManager.clearUnreadMessage()

}