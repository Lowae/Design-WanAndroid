package com.lowe.wanandroid.ui.message

import android.util.Log
import com.lowe.wanandroid.account.IAccountViewModelDelegate
import com.lowe.wanandroid.account.isLogin
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.di.ApplicationScope
import com.lowe.wanandroid.di.IoDispatcher
import com.lowe.wanandroid.services.usecase.UnreadMessageCountUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
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