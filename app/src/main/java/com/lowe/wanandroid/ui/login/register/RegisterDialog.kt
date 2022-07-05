package com.lowe.wanandroid.ui.login.register

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.account.RegisterInfo
import com.lowe.wanandroid.databinding.DialogRegisterAccountLayoutBinding
import com.lowe.wanandroid.services.model.isSuccess
import com.lowe.wanandroid.ui.login.LoginActivity
import com.lowe.wanandroid.ui.login.LoginViewModel
import com.lowe.wanandroid.utils.showShortToast

class RegisterDialog(activity: LoginActivity, viewModel: LoginViewModel) {

    val userNameObservable = ObservableField<String>()
    val passwordObservable = ObservableField<String>()
    val confirmPasswordObservable = ObservableField<String>()
    val loginEnable = object :
        ObservableBoolean(userNameObservable, passwordObservable, confirmPasswordObservable) {
        override fun get() =
            userNameObservable.get()?.trim().isNullOrBlank().not()
                    && passwordObservable.get()?.trim().isNullOrBlank().not()
                    && confirmPasswordObservable.get()?.trim().isNullOrBlank().not()
    }

    private val dialogViewDataBinding: DialogRegisterAccountLayoutBinding =
        DataBindingUtil.inflate<DialogRegisterAccountLayoutBinding?>(
            LayoutInflater.from(activity),
            R.layout.dialog_register_account_layout,
            null,
            false
        ).also {
            it.lifecycleOwner = activity
        }

    private val registerDialog by lazy(LazyThreadSafetyMode.NONE) {
        MaterialAlertDialogBuilder(activity).setView(dialogViewDataBinding.root)
            .setCancelable(true)
            .create()
    }

    init {
        dialogViewDataBinding.apply {
            dialog = this@RegisterDialog
            loginButton.setOnClickListener {
                val name = userNameObservable.get()?.trim().toString()
                val password = passwordObservable.get()?.trim().toString()
                val confirmPassword = confirmPasswordObservable.get()?.trim().toString()
                if (checkRegisterStatus(name, password, confirmPassword)) {
                    viewModel.register(RegisterInfo(name, password, confirmPassword))
                    dialogViewDataBinding.Loading.isVisible = true
                }
            }
            userName.doAfterTextChanged {
                if (userNameInputLayout.error.isNullOrBlank().not()) userNameInputLayout.error = ""
            }
            password.doAfterTextChanged {
                if (passwordInputLayout.error.isNullOrBlank().not()) passwordInputLayout.error = ""
            }
            confirmPassword.doAfterTextChanged {
                if (confirmPasswordInputLayout.error.isNullOrBlank()
                        .not()
                ) confirmPasswordInputLayout.error = ""
            }

        }
        viewModel.registerLiveData.observe(activity) {
            if (it.isSuccess()) {
                registerDialog.dismiss()
                activity.getString(R.string.register_success).showShortToast()
            } else it.errorMsg.showShortToast()
            dialogViewDataBinding.Loading.isVisible = false
        }
    }

    fun show() {
        registerDialog.show()
    }

    private fun checkRegisterStatus(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.length < 3) {
            dialogViewDataBinding.userNameInputLayout.error = "用户名最少3位"
            return false
        }
        if (password.length < 6) {
            dialogViewDataBinding.passwordInputLayout.error = "密码至少 6 位"
            return false
        }
        if (confirmPassword.length < 6) {
            dialogViewDataBinding.confirmPasswordInputLayout.error = "密码至少 6 位"
            return false
        }
        if (password != confirmPassword) {
            dialogViewDataBinding.confirmPasswordInputLayout.error = "确认密码与密码不符"
            return false
        }
        return true
    }

}