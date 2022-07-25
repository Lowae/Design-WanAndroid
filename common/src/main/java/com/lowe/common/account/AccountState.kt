package com.lowe.common.account

import android.content.Context
import com.lowe.common.services.model.User
import com.lowe.common.utils.Activities.Login
import com.lowe.common.utils.intentTo
import com.lowe.common.utils.showShortToast

sealed interface AccountState {

    object LogOut : AccountState

    data class LogIn(val isFromCookie: Boolean, val user: User? = null) : AccountState
}

inline val AccountState.isLogin: Boolean
    get() {
        return this is AccountState.LogIn
    }

inline fun AccountState.checkLogin(context: Context, action: (AccountState) -> Unit) {
    if (this.isLogin) {
        action(this)
    } else {
        context.getString(com.lowe.resource.R.string.account_need_login).showShortToast()
        context.startActivity(intentTo(Login))
    }
}