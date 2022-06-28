package com.lowe.wanandroid.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.lowe.wanandroid.R
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.databinding.ActivityLoginBinding
import com.lowe.wanandroid.services.isSuccess
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override val viewDataBinding: ActivityLoginBinding by ActivityDataBindingDelegate(R.layout.activity_login)
    override val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.activity_anim_login_in, R.anim.activity_anim_dummy)
        super.onCreate(savedInstanceState)
        initView()
        initObserve()
    }

    private fun initView() {
        viewDataBinding.loginButton.setOnClickListener {
            updateLoginLoadingStatus(true)
            viewModel.login(
                LocalUserInfo(
                    viewDataBinding.editName.text.toString(),
                    viewDataBinding.editPassword.text.toString()
                )
            )
        }
        viewDataBinding.backIcon.setOnClickListener { finish() }
    }

    private fun initObserve() {
        viewModel.apply {
            loginLiveData.observe(this@LoginActivity) {
                updateLoginLoadingStatus(false)
                if (it.isSuccess()) finish() else it.errorMsg.showShortToast()
            }
        }
    }

    private fun updateLoginLoadingStatus(isLoading: Boolean) {
        viewDataBinding.loginLoading.isVisible = isLoading
    }
}