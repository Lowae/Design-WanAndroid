package com.lowe.wanandroid.ui.home

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.ui.BaseViewModel

class HomeViewModel : BaseViewModel() {

    val scrollToTopLiveData = MutableLiveData<HomeTabBean>()
    val refreshLiveData = MutableLiveData<HomeTabBean>()

    override fun start() {

    }
}