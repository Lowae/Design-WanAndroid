package com.lowe.wanandroid.ui.login

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityLoginBinding
import com.lowe.wanandroid.ui.BaseActivity

class LoginActivity: BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

    override fun createViewModel() = LoginViewModel()

    override fun init(savedInstanceState: Bundle?) {
    }
}