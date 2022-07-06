package com.lowe.wanandroid.base.http.adapter

/**
 * 用于配置全局的异常处理逻辑
 */
interface ErrorHandler {

    /**
     * 业务错误
     */
    fun bizError(code: Int, msg: String)

    /**
     * 其他错误
     */
    fun otherError(throwable: Throwable)
}