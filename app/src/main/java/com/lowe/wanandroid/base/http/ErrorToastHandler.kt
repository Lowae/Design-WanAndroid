package com.lowe.wanandroid.base.http

import com.lowe.wanandroid.base.app.BaseApp
import com.lowe.wanandroid.base.http.adapter.ErrorHandler
import com.lowe.wanandroid.utils.NetWorkUtil
import com.lowe.wanandroid.utils.showShortToast
import java.io.IOException

/**
 * Error的Toast处理
 */
internal object ErrorToastHandler : ErrorHandler {

    private const val ERROR_DEFAULT = "请求失败"
    private const val ERROR_NET_WORK_DISCONNECTED = "网络连接异常"

    private fun handle(throwable: Throwable): String =
        when (throwable) {
            is IOException -> {
                if (NetWorkUtil.isNetworkAvailable(BaseApp.appContext).not()) {
                    ERROR_NET_WORK_DISCONNECTED
                } else ERROR_DEFAULT
            }
            else -> ERROR_DEFAULT
        }

    override fun bizError(code: Int, msg: String) {
        msg.showShortToast()
    }

    override fun otherError(throwable: Throwable) {
        handle(throwable).showShortToast()
    }
}