package com.lowe.wanandroid.account

data class LocalUserInfo(
    val username: String = "",
    val password: String = ""
) {

    fun isValid() = username.isNotBlank() && password.isNotBlank()

}