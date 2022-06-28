package com.lowe.wanandroid

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.ui.BaseViewModel

class MainViewModel: BaseViewModel() {

    val mainTabDoubleClickLiveData = MutableLiveData<String>()

    override fun init() {

    }
}