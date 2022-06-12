package com.lowe.wanandroid.services

import com.lowe.wanandroid.utils.ToastEx.showShortToast

/**
 * 接口返回外层封装实体
 *
 */
data class ApiResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)

data class PageResponse<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

fun <T> ApiResponse<T>.isSuccess() = errorCode == 0 && errorMsg == ""

fun <T> ApiResponse<T>.success(): ApiResponse<T>? {
    return if (errorCode == 0) {
        return this
    } else {
        errorMsg.showShortToast()
        null
    }
}