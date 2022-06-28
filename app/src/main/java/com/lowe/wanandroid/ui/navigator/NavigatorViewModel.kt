package com.lowe.wanandroid.ui.navigator

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.ui.BaseViewModel

class NavigatorViewModel : BaseViewModel() {

    val scrollToTopLiveData = MutableLiveData<NavigatorTabBean>()
    val refreshLiveData = MutableLiveData<NavigatorTabBean>()

    override fun init() {

    }
}