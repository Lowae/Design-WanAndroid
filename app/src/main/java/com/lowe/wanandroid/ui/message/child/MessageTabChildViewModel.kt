package com.lowe.wanandroid.ui.message.child

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.message.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageTabChildViewModel @Inject constructor(private val repository: MessageRepository) :
    BaseViewModel() {

    fun getReadiedMsgFlow() = repository.getReadiedMsgFlow()

    fun getUnreadMsgFlow() = repository.getUnreadMsgFlow()
}