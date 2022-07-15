package com.lowe.wanandroid.ui.message

import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.di.ApplicationScope
import com.lowe.wanandroid.di.IoDispatcher
import com.lowe.wanandroid.services.usecase.UnreadMessageCountUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnreadMessageManager @Inject constructor(
    private val unreadMessageCountUseCase: UnreadMessageCountUseCase,
    @ApplicationScope applicationScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


    private val _unreadMessage: MutableStateFlow<UnreadMessage> = MutableStateFlow(UnreadMessage(0))
    val unreadMessage: StateFlow<UnreadMessage> = _unreadMessage

    private val profileUnread = MutableStateFlow(true)

    val profileUnreadState =
        unreadMessage.map { it.count != 0 }.combine(profileUnread) { unread, profile ->
            unread && profile
        }

    init {
        applicationScope.launch(ioDispatcher) {
            _unreadMessage.emit(
                UnreadMessage(
                    unreadMessageCountUseCase(Unit).getOrNull() ?: 0
                )
            )
        }
    }


    fun clearProfileUnread() {
        profileUnread.tryEmit(false)
    }

    fun clearUnreadMessage() {
        _unreadMessage.tryEmit(UnreadMessage(0))
    }

}