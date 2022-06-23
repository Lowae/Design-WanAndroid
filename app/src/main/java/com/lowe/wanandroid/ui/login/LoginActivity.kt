package com.lowe.wanandroid.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.lowe.wanandroid.R
import com.lowe.wanandroid.account.AccountState
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.databinding.ActivityLoginBinding
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

    companion object {
        const val REQUEST_CODE_TO_LOGIN_ACTIVITY = 100
        const val LOGIN_RESULT_STATE_LOGIN_SUCCESS = 1
        const val LOGIN_RESULT_STATE_LOGIN_FAIL = -1
    }

    override val viewModel: LoginViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initObserve()

        viewDataBinding.loginButton.setOnClickListener {
            viewModel.login(
                LocalUserInfo(
                    viewDataBinding.editName.text.toString(),
                    viewDataBinding.editPassword.text.toString()
                )
            )
        }
        viewModel.getUserInfo().let {
            if (it is AccountState.LogIn) {
                viewDataBinding.loginInfo.text = it.userInfo.toString()
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            loginLiveData.observe(this@LoginActivity) {
                if (it.isSuccess()) {
                    viewDataBinding.loginInfo.text = it.toString()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    it.errorMsg.showShortToast()
                }
            }
        }
    }
}