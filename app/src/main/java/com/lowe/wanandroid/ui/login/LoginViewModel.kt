package com.lowe.wanandroid.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.services.ApiResponse
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    BaseViewModel() {

    val loginLiveData = MutableLiveData<ApiResponse<User>>()

    fun login(userInfo: LocalUserInfo) {
        launch({
            Log.d("LoginViewModel", "login: $userInfo")
            loginLiveData.value = repository.login(userInfo)
            Log.d("LoginViewModel", "login: $userInfo")
        })
    }

    fun getUserInfo() = repository.getUserInfo()
}