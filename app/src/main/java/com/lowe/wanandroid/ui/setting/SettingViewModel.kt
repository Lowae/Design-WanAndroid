package com.lowe.wanandroid.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.account.IAccountViewModelDelegate
import com.lowe.wanandroid.base.http.adapter.whenSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val accountViewModelDelegate: IAccountViewModelDelegate) :
    ViewModel(), IAccountViewModelDelegate by accountViewModelDelegate {

    private val _logoutLiveData = MutableLiveData<Any>()
    val logoutLiveData: LiveData<Any> = _logoutLiveData

    fun userLogout() {
        viewModelScope.launch {
            logout().whenSuccess {
                _logoutLiveData.value = it
            }
        }
    }

}