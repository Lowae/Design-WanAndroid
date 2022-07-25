package com.lowe.wanandroid.ui.profile

import com.lowe.common.base.BaseViewModel
import com.lowe.common.account.IAccountViewModelDelegate
import com.lowe.wanandroid.ui.message.UnreadMessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val unreadMessageManager: UnreadMessageManager,
    private val accountViewModelDelegate: IAccountViewModelDelegate
) : BaseViewModel(), IAccountViewModelDelegate by accountViewModelDelegate {


    val messageUnreadState = unreadMessageManager.unreadMessage

    fun clearProfileUnread() = unreadMessageManager.clearProfileUnread()

}