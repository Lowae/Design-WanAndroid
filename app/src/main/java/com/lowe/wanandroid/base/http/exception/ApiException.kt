package com.lowe.wanandroid.base.http.exception

class ApiException(var code: Int, override var message: String?) : RuntimeException(message) {

    companion object {
        const val CODE_NOT_LOGGED_IN = -1001
    }

    fun isNotLogged() = code == CODE_NOT_LOGGED_IN

}