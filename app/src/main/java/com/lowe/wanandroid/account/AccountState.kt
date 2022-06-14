package com.lowe.wanandroid.account

sealed interface AccountState {

    object LogOut : AccountState

    data class LogIn(val userInfo: LocalUserInfo) : AccountState

}