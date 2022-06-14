package com.lowe.wanandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.services.ApiResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    companion object {

        const val DEFAULT_PAGE_SIZE = 20

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            AppLog.e(msg = throwable.message.toString())
        }
    }

    val requestException = MutableLiveData<Exception>()

    val responseException = MutableLiveData<ApiResponse<*>>()

    /**
     * 初始化逻辑
     */
    open fun start() = Unit
}

fun BaseViewModel.launch(
    apiCall: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.() -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
) {


    this.viewModelScope.launch(BaseViewModel.exceptionHandler) {
        try {
            apiCall()
        } catch (e: Exception) {
            requestException.value = e
            catchBlock()
        } finally {
            finallyBlock()
        }
    }
}

suspend fun <T> BaseViewModel.response(
    vararg response: ApiResponse<T>,
    successBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit = {},
    errorBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Boolean = { false }
) {
    coroutineScope {
        response.forEach {
            when (it.errorCode) {
                0 -> successBlock(it) // 服务器返回请求成功码
                else -> { // 服务器返回的其他错误码
                    if (!errorBlock(it)) {
                        // 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
                        responseException.value = it
                    }
                }
            }
        }
    }
}