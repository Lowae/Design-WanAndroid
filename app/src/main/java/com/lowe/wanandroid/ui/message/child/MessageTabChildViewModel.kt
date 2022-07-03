package com.lowe.wanandroid.ui.message.child

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.message.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageTabChildViewModel @Inject constructor(private val repository: MessageRepository) :
    BaseViewModel() {

    val getReadiedMsgFlow = repository.getReadiedMsgFlow().cachedIn(viewModelScope)

    val getUnreadMsgFlow = repository.getUnreadMsgFlow().cachedIn(viewModelScope)
}