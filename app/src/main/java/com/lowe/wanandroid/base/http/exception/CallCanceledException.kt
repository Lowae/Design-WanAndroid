package com.lowe.wanandroid.base.http.exception

import java.io.IOException

class CallCanceledException(throwable: Throwable) : IOException(throwable) {

    fun isSuppressed() = true

}