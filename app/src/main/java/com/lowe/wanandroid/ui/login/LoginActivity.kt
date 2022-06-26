package com.lowe.wanandroid.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.lowe.wanandroid.R
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.databinding.ActivityLoginBinding
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

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
    }

    private fun initObserve() {
        viewModel.apply {
            loginLiveData.observe(this@LoginActivity) {
                if (it.isSuccess()) finish() else it.errorMsg.showShortToast()
            }
        }
    }
}