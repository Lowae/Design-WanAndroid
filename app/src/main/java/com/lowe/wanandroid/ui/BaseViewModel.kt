package com.lowe.wanandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.base.AppLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    companion object {

        const val DEFAULT_PAGE_SIZE = 20

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            AppLog.e(msg = throwable.message.toString())
        }
    }

    val requestException = MutableLiveData<Exception>()

    /**
     * 初始化逻辑
     */
    open fun init() = Unit
}

fun BaseViewModel.launch(
    call: suspend CoroutineScope.() -> Unit,
    catch: suspend CoroutineScope.() -> Unit = {},
    finally: suspend CoroutineScope.() -> Unit = {}
) {
    this.viewModelScope.launch(BaseViewModel.exceptionHandler) {
        try {
            call()
        } catch (e: Exception) {
            requestException.postValue(e)
            catch()
        } finally {
            finally()
        }
    }
}