package com.lowe.wanandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.ui.BaseViewModel

class MainViewModel : BaseViewModel() {

    /**
     * 首页NavigationBottomTab双击事件
     */
    private val _mainTabDoubleClickLiveData = MutableLiveData<String>()
    val mainTabDoubleClickLiveData: LiveData<String> = _mainTabDoubleClickLiveData

    fun bottomDoubleClick(tag: String) {
        _mainTabDoubleClickLiveData.value = tag
    }

    override fun init() {

    }
}