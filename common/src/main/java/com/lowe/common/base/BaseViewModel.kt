package com.lowe.common.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    companion object {

        const val DEFAULT_PAGE_SIZE = 20
    }
}