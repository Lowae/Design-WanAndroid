package com.lowe.wanandroid.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.lowe.wanandroid.R
import com.lowe.wanandroid.account.LocalUserInfo
import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.databinding.ActivityLoginBinding
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.login.register.RegisterDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录页面
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    private val registerDialog by lazy(LazyThreadSafetyMode.NONE) {
        RegisterDialog(
            this,
            viewModel
        )
    }

    override val viewDataBinding: ActivityLoginBinding by ActivityDataBindingDelegate(R.layout.activity_login)
    override val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.activity_anim_login_in, R.anim.activity_anim_dummy)
        super.onCreate(savedInstanceState)
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.loginButton.setOnClickListener {
            updateLoginLoadingStatus(true)
            viewModel.userLogin(
                LocalUserInfo(
                    viewModel.userNameObservable.get().toString(),
                    viewModel.passwordObservable.get().toString()
                )
            )
        }
        viewDataBinding.register.setOnClickListener { registerDialog.show() }
        viewDataBinding.backIcon.setOnClickListener { finish() }
    }

    private fun initEvents() {
        viewModel.apply {
            loginLiveData.observe(this@LoginActivity) {
                updateLoginLoadingStatus(false)
                if (it is NetworkResponse.Success) finish()
            }
        }
    }

    private fun updateLoginLoadingStatus(isLoading: Boolean) {
        viewDataBinding.loginLoading.isVisible = isLoading
    }
}