package com.lowe.wanandroid.base.app

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.ui.BaseViewModel

class AppViewModel : BaseViewModel() {

    val userLiveData = MutableLiveData<User>()

}