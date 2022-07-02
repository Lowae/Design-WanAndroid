package com.lowe.wanandroid.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.services.model.ApiResponse
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.repository.AccountRepository
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    BaseViewModel() {

    private val _loginLiveData = MutableLiveData<ApiResponse<User>>()
    val loginLiveData: LiveData<ApiResponse<User>> = _loginLiveData

    /**
     * 数据绑定
     */
    val userNameObservable = ObservableField<String>()
    val passwordObservable = ObservableField<String>()
    val loginEnable = object : ObservableBoolean(userNameObservable, passwordObservable) {
        override fun get() =
            userNameObservable.get()?.trim().isNullOrBlank().not() && passwordObservable.get()
                ?.trim().isNullOrBlank().not()
    }

    fun login(userInfo: LocalUserInfo) {
        launch({
            _loginLiveData.value = accountRepository.login(userInfo)
        })
    }
}