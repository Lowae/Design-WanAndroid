package com.lowe.wanandroid.ui.navigator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.common.base.BaseViewModel

class NavigatorViewModel : BaseViewModel() {

    private val _scrollToTopLiveData = MutableLiveData<NavigatorTabBean>()
    val scrollToTopLiveData: LiveData<NavigatorTabBean> = _scrollToTopLiveData

    fun scrollToTopEvent(tabBean: NavigatorTabBean) {
        _scrollToTopLiveData.value = tabBean
    }
}