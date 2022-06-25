package com.lowe.wanandroid.base.http.exception

class ApiException(var code: Int, override var message: String?) : RuntimeException(message) {

    companion object {
        const val UNKNOWN_ERR_CODE = -1001
    }

    fun code(): Int {
        return code
    }

    fun message(): String? {
        return message
    }
}