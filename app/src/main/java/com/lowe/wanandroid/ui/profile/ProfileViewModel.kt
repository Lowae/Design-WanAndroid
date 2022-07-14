package com.lowe.wanandroid.ui.profile

import androidx.annotation.DrawableRes
import com.lowe.wanandroid.account.IAccountViewModelDelegate
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountViewModelDelegate: IAccountViewModelDelegate
) : BaseViewModel(), IAccountViewModelDelegate by accountViewModelDelegate

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