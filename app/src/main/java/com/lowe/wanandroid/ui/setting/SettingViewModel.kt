package com.lowe.wanandroid.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.base.http.adapter.whenSuccess
import com.lowe.wanandroid.services.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    private val _logoutLiveData = MutableLiveData<NetworkResponse<Any>>()
    val logoutLiveData: LiveData<NetworkResponse<Any>> = _logoutLiveData

    fun logout() {
        viewModelScope.launch {
            accountRepository.logout().whenSuccess {
                _logoutLiveData.value = it
            }
        }
    }

}