package com.lowe.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.ui.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _scrollToTopLiveData = MutableLiveData<HomeTabBean>()
    val scrollToTopLiveData: LiveData<HomeTabBean> = _scrollToTopLiveData
    private val _refreshLiveData = MutableLiveData<HomeTabBean>()
    val refreshLiveData: LiveData<HomeTabBean> = _refreshLiveData

    fun scrollToTopEvent(tabBean: HomeTabBean) {
        _scrollToTopLiveData.value = tabBean
    }

    fun refreshEvent(tabBean: HomeTabBean) {
        _refreshLiveData.value = tabBean
    }

    override fun init() {

    }
}