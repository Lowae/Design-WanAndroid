package com.lowe.wanandroid.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.services.model.ApiResponse
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.services.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    private val _logoutLiveData = MutableLiveData<ApiResponse<Any?>>()
    val logoutLiveData: LiveData<ApiResponse<Any?>> = _logoutLiveData

    fun logout() {
        viewModelScope.launch {
            accountRepository.logout().success()?.also {
                _logoutLiveData.value = it
            }
        }
    }

}