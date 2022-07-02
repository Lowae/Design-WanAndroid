package com.lowe.wanandroid.ui.profile

import androidx.annotation.DrawableRes
import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.profile.usecase.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
) : BaseViewModel() {

    fun fetchUserInfo() {
        launch({
            userInfoUseCase.getServerUserInfo()
                .success()?.data?.apply {
                    AccountManager.cacheUserBaseInfo(this)
                }
        })
    }

    fun userStatusFlow() = userInfoUseCase.accountStatusFlow()
}

/**
 * 个人页选项
 */
class ProfileItemBean(
    @DrawableRes
    val iconRes: Int,
    val title: String,
    val type: Type = Type.Action
) {
    sealed interface Type {

        object Action : Type

    }

}