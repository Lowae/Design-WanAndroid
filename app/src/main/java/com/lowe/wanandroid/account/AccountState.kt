package com.lowe.wanandroid.account

import android.content.Context
import com.lowe.wanandroid.R
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.utils.Activities.Login
import com.lowe.wanandroid.utils.intentTo
import com.lowe.wanandroid.utils.showShortToast

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
        context.getString(R.string.account_need_login).showShortToast()
        context.startActivity(intentTo(Login))
    }
}