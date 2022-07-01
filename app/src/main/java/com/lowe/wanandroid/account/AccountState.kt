package com.lowe.wanandroid.account

import android.content.Context
import com.lowe.wanandroid.R
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.intentTo
import com.lowe.wanandroid.utils.showShortToast

sealed interface AccountState {

    object LogOut : AccountState

    data class LogIn(val isFromCookie: Boolean, val user: User? = null) : AccountState
}

inline fun AccountState.checkLogin(context: Context, action: (AccountState) -> Unit) {
    if (this is AccountState.LogOut) {
        context.getString(R.string.account_need_login).showShortToast()
        context.startActivity(intentTo(Activities.Login))
    } else action(this)
}