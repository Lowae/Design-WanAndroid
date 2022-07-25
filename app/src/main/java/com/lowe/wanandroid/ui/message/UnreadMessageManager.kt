package com.lowe.wanandroid.ui.message

import com.lowe.common.base.http.adapter.getOrNull
import com.lowe.common.di.ApplicationScope
import com.lowe.common.di.IoDispatcher
import com.lowe.common.services.usecase.UnreadMessageCountUseCase
import com.lowe.common.account.IAccountViewModelDelegate
import com.lowe.common.account.isLogin
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
    private val accountViewModelDelegate: IAccountViewModelDelegate,
    @ApplicationScope applicationScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IAccountViewModelDelegate by accountViewModelDelegate {


    private val _unreadMessage: MutableStateFlow<UnreadMessage> = MutableStateFlow(UnreadMessage(0))
    val unreadMessage: StateFlow<UnreadMessage> = _unreadMessage

    private val profileUnread = MutableStateFlow(true)

    val profileUnreadState =
        unreadMessage.map { it.count != 0 }.combine(profileUnread) { unread, profile ->
            unread && profile
        }

    init {

        applicationScope.launch(ioDispatcher) {
            accountViewModelDelegate.accountState.collect {
                _unreadMessage.emit(
                    UnreadMessage(
                        if (it.isLogin) unreadMessageCountUseCase(Unit).getOrNull() ?: 0 else 0
                    )
                )
             }
        }
    }


    fun clearProfileUnread() {
        profileUnread.tryEmit(false)
    }

    fun clearUnreadMessage() {
        _unreadMessage.tryEmit(UnreadMessage(0))
    }

}