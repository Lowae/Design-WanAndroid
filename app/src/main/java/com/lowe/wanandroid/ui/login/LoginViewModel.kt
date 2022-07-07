package com.lowe.wanandroid.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.account.RegisterInfo
import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.repository.AccountRepository
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    BaseViewModel() {

    private val _loginLiveData = MutableLiveData<NetworkResponse<User>>()
    val loginLiveData: LiveData<NetworkResponse<User>> = _loginLiveData

    private val _registerLiveData = MutableLiveData<NetworkResponse<Any>>()
    val registerLiveData: LiveData<NetworkResponse<Any>> = _registerLiveData

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

    fun register(registerInfo: RegisterInfo) {
        launch({
            _registerLiveData.value = accountRepository.register(registerInfo)
        })
    }
}